package com.example.shivrana.shivrana_finaltest_003;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button newBooks, showBookDetails, buyBook;
    TextView showBookDetailsTxtView;
    Spinner bookISBN;
    BookManager bookManager;
    ArrayList<Book> bookData;
    String bookInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookManager = new BookManager(this);
        bookManager.setTableName("Book");
        bookManager.setTableCreatorString("CREATE TABLE `Book` (`bookISBN` Integer Primary Key, `bookName` Text, `authorName` Text, `price` Real)");

        declaration();
        BtnEvents();
        spinnerEvent();

        //Populating spinner with isbn
        populatingSpinner();

    }

    public void declaration(){
        newBooks = findViewById(R.id.newBooks);
        showBookDetails = findViewById(R.id.showBooksDetails);
        buyBook = findViewById(R.id.buyBook);
        showBookDetailsTxtView = findViewById(R.id.bookInformation);
        bookISBN = findViewById(R.id.bookISBN);
        bookData = new ArrayList<>();
    }

    public void BtnEvents(){
        newBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookManager.addBooks();
                Toast.makeText(MainActivity.this, "Books Added", Toast.LENGTH_SHORT).show();
                populatingSpinner();
            }
        });

        showBookDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, bookInfo, Toast.LENGTH_SHORT).show();
            }
        });

        buyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this,webViewActivity.class);
                startActivity(myintent);
            }
        });

    }

    public void spinnerEvent(){
        bookISBN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                   Book book2 =  bookManager.getBookById(parent.getSelectedItem().toString(),"BookISBN");
                    showBookDetailsTxtView.setText(String.format("Book Name : %s\n Author Name :%s\n Price :%f\n",book2.getBookName(),book2.getAuthorName(),book2.getPrice()));
                    bookInfo = String.format("Book Name : %s\n Author Name :%s\n Price :%f",book2.getBookName(),book2.getAuthorName(),book2.getPrice());
                }
                catch (Exception ex){
                    Log.i("ERROR : ", ex.getMessage());
                }
                //showBookDetails.setText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void populatingSpinner(){
        ArrayAdapter<Integer> adp = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        bookData = bookManager.getBooks();
        for (Book b: bookData) {
            adp.add(b.getBookISBN());
        }
        bookISBN.setAdapter(adp);
    }

}
