package kr.ac.jbnu.se.mybook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import kr.ac.jbnu.se.mybook.R;

public class BookMainPage extends ActionBarActivity {
    private Button bookButton;
    private Button imgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mainpage);
        Button bookButton = (Button) findViewById(R.id.bookButton);
        Button imgButton = (Button) findViewById(R.id.imgButton);
        bookButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), BookListActivity.class);
                startActivity(intent);
            }
        });
        imgButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ImageListActivity.class);
                startActivity(intent);
            }
        });
    }
}
