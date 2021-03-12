package uyennlp.demo.demosqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, edtContact, edtDob;
    private Button btnInsert, btnUpdate, btnDelete, btnView;
    private DBHelper manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtName = findViewById(R.id.edtName);
        edtContact = findViewById(R.id.edtContact);
        edtDob = findViewById(R.id.edtDob);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);

        manager = new DBHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String contact = edtContact.getText().toString();
                String dob = edtDob.getText().toString();

                boolean check = manager.insert(name, contact, dob);
                if (check) {
                    Toast.makeText(MainActivity.this, "Inserted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String contact = edtContact.getText().toString();
                String dob = edtDob.getText().toString();

                boolean check = manager.update(name, contact, dob);
                if (check) {
                    Toast.makeText(MainActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();

                boolean check = manager.delete(name);
                if (check) {
                    Toast.makeText(MainActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DTO> dtos = manager.view();

                if (dtos.size() == 0 || dtos == null) {
                    Toast.makeText(MainActivity.this, "No record!", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer sb = new StringBuffer();
                for (DTO dto : dtos) {
                    sb.append("Name : " + dto.getName() + "\n");
                    sb.append("Contact : " + dto.getContact() + "\n");
                    sb.append("D.O.B : " + dto.getDob() + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(sb.toString());
                builder.show();
            }
        });
    }
}