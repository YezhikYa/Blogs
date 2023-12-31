package com.yezhik_ya.blogs.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.yezhik_ya.blogs.R;
import com.yezhik_ya.helper.AlertUtil;
import com.yezhik_ya.helper.DateUtil;
import com.yezhik_ya.helper.inputValidators.DateRule;
import com.yezhik_ya.helper.inputValidators.EntryValidation;
import com.yezhik_ya.helper.inputValidators.NameRule;
import com.yezhik_ya.helper.inputValidators.Rule;
import com.yezhik_ya.helper.inputValidators.RuleOperation;
import com.yezhik_ya.helper.inputValidators.TextRule;
import com.yezhik_ya.helper.inputValidators.Validator;
import com.yezhik_ya.model.BlogPost;
import com.yezhik_ya.viewmodel.BlogsViewModel;
import com.yezhik_ya.viewmodel.GenericViewModelFactory;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class BlogPostActivity extends BaseActivity implements EntryValidation
{
    private EditText etAuthor, etTitle, etContent, etDate;
    private ImageButton ibCalendar;
    private Button btnSave, btnCancel;
    private BlogsViewModel blogsViewModel;
    private BlogPost oldBlogPost;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_post);

        initializeViews();

        setObservers();
    }

    @Override
    protected void initializeViews()
    {
        etAuthor = findViewById(R.id.etAuthor);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        etDate = findViewById(R.id.etDate);
        ibCalendar = findViewById(R.id.ibCalendar);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        setListeners();
    }

    @Override
    protected void setListeners()
    {
        ibCalendar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Select date");
                builder.setTextInputFormat(new SimpleDateFormat("dd/MM/yyyy"));

                CalendarConstraints constraint = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    constraint = DateUtil.buidCalendarConstrains(LocalDate.now().minusDays(10), LocalDate.now());

                builder.setCalendarConstraints(constraint);

                if (!etDate.getText().toString().isEmpty())
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        LocalDateTime date = LocalDate.parse(etDate.getText().toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
                        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
                        builder.setSelection(zdt.toInstant().toEpochMilli());
                    }
                }

                MaterialDatePicker picker = builder.build();

                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener()
                {
                    @Override
                    public void onPositiveButtonClick(Object selection)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        {
                            etDate.setText(Instant.ofEpochMilli((long) selection).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                            etDate.setError(null);
                        }
                        else
                            etDate.setText("ERROR !!!");
                    }
                });

                picker.addOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialog)
                    {
                        AlertUtil.alertOk(BlogPostActivity.this, "Date", "Please enter post date", true, R.drawable.exclamation_mark);
                    }
                });

                picker.setCancelable(true);
                picker.show(getSupportFragmentManager(), "DATE PICKER");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(validate())
                {
                    BlogPost blogPost = new BlogPost();
                    blogPost.setAuthor(etAuthor.getText().toString());
                    blogPost.setTitle(etTitle.getText().toString());
                    blogPost.setDate(DateUtil.stringDateToLong(etDate.getText().toString()));
                    blogPost.setContent(etContent.getText().toString());
                    if (oldBlogPost != null)
                        blogPost.setIdFs(oldBlogPost.getIdFs());

                    blogsViewModel.add(blogPost);

                    Intent intent = new Intent(getApplicationContext(), PostsActivity.class);
                    intent.putExtra("POST", blogPost);
                    startActivity(intent);
                    //setResult(RESULT_OK, intent);
                    //finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { finish(); }
        });
    }

    @Override
    public void setValidation()
    {

        Validator.add(new Rule(etAuthor, RuleOperation.REQUIRED, "Please enter author name"));

        Validator.add(new NameRule(etAuthor, RuleOperation.NAME, "Author name is wrong"));

        Validator.add(new Rule(etTitle, RuleOperation.REQUIRED, "Please enter title"));

        Validator.add(new TextRule(etTitle, RuleOperation.TEXT, "Title is wrong", 4, 50, true));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            Validator.add(new DateRule(etDate, RuleOperation.DATE, "Wrong date", LocalDate.now().minusDays(10), LocalDate.now()));

        Validator.add(new Rule(etContent, RuleOperation.REQUIRED, "Please enter content"));

        Validator.add(new TextRule(etContent, RuleOperation.TEXT, "Wrong content", 10, 1000, true));
    }
    @Override
    public boolean validate()
    {
        return Validator.validate();
    }
    private void setObservers()
    {
        GenericViewModelFactory<BlogsViewModel> factory = new GenericViewModelFactory<> (getApplication(), BlogsViewModel::new);
        blogsViewModel = new ViewModelProvider(this, factory).get(BlogsViewModel.class);
        blogsViewModel.getSuccessOperation().observe(this, new Observer<Boolean>()
        {
                @Override
                public void onChanged(Boolean aBoolean)
                {
                    if (aBoolean)
                        Toast.makeText(BlogPostActivity.this, "Saved successfully !", Toast.LENGTH_SHORT).show();
                }
        });
    }
}