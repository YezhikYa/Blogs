package com.yezhik_ya.blogs.Activities;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.yezhik_ya.blogs.R;
import com.yezhik_ya.viewmodel.BlogsViewModel;

import java.io.ObjectInputValidation;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class BlogPostActivity extends BaseActivity
{
    private BlogsViewModel blogsViewModel;
    private EditText etAuthor, etTitle, etContent, etDate;
    private ImageButton ibCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_post);
    }

    @Override
    protected void initializeViews()
    {
        etAuthor = findViewById(R.id.etAuthor);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        etDate = findViewById(R.id.etDate);
        ibCalendar = findViewById(R.id.ibCalendar);

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

                // הגבלת טווח התאריכים לבחירה
                CalendarConstraints constraint = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    constraint = DateUtil.buidCalendarConstrains(LocalDate.now().minusDays(10), LocalDate.now());

                builder.setCalendarConstraints(constraint);

                // במידה ויש תאריך בתיבת בקלט
                // התאריכון יפתח על התאריך הרשום
                if (!etDate.getText().toString().isEmpty())
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        LocalDateTime date = LocalDate.parse(etDate.getText().toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
                        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
                        builder.setSelection(zdt.toInstant().toEpochMilli());
                    }
                }


                MaterialDatePicker picker = builder.build();


                // אישור התאריך
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

                // ביטול התאריך
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
    }

    @Override
    public void setValidation()
    {

        Validator.add(new Rule(etAuthor, RuleOperation.REQUIRED, "Please enter author name"));

        Validator.add(new NameRule(etAuthor, RuleOperation.NAME, "Author name is wrong"));

        Validator.add(new Rule(etTitle, RuleOperation.REQUIRED, "Please enter title"));

        Validator.add(new TextRule(etTitle, RuleOperation.TEXT, "Title is wrong", 4, 50, true));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Validator.add(new DateRule(etDate, RuleOperation.DATE, "Wrong date", LocalDate.now().minusDays(10), LocalDate.now()));
        };

        Validator.add(new Rule(etContent, RuleOperation.REQUIRED, "Please enter content"));

        Validator.add(new TextRule(etContent, RuleOperation.TEXT, "Wrong content", 10, 1000, true));
    }
    @Override
    public boolean validate()
    {
        return Validator.validate();
    }
}