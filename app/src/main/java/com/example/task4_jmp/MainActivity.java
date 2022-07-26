package com.example.task4_jmp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int emptyX = 3;
    private int emptyY = 3;
    private RelativeLayout group;
    private Button[][] buttons;
    private int[] tiles;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resources = getResources();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadViews();
        showPopup();
//        loadNumbers();
//        generateNumbers();
//        loadDataToViews();
    }

    private void showPopup() {
        Button closeBtn;
        Dialog dialog = new Dialog(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutPopup);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.popup, null);

        closeBtn = view.findViewById(R.id.closePopup);
        System.out.println(closeBtn.getText().toString());
        dialog.setContentView(R.layout.popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("lol    llllllll                lllllllll    ");
                dialog.cancel();
            }
        });
    }

    private void loadDataToViews() {
        emptyX = 3;
        emptyY = 3;

        for (int i = 0; i < group.getChildCount() - 1; i++) {
            buttons[i / 4][i % 4].setText(String.valueOf(tiles[i]));
        }

        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(Color.WHITE);
    }

    private void generateNumbers() {
        int n = 15;
        Random random = new Random();

        while (n > 1) {
            int randomNum = random.nextInt(n--);
            int temp = tiles[randomNum];
            tiles[randomNum] = tiles[n];
            tiles[n] = temp;
        }

        if (!isSolvable()) {
            generateNumbers();
        }
    }

    private boolean isSolvable() {
        int countInversion = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i]) {
                    countInversion++;
                }
            }
        }

        return (countInversion % 2 == 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    private void loadViews() {
        group = findViewById(R.id.group);
        buttons = new Button[4][4];

        for (int i = 0; i < group.getChildCount(); i++) {
            buttons[i / 4][i % 4] = (Button) group.getChildAt(i);
        }
    }

    private void loadNumbers() {
        tiles = new int[16];

        for (int i = 0; i < (group.getChildCount() - 1); i++) {
            tiles[i] = i+1;
        }
    }

    public void buttonClick(View view) {
        Button button = (Button) view;
        System.out.println("x " + button.getTag().toString().charAt(0));
        System.out.println("y " + button.getTag().toString().charAt(1));
        int x = button.getTag().toString().charAt(0) - '0';
        int y = button.getTag().toString().charAt(1) - '0';

        if ((Math.abs(emptyX - x) == 1 && emptyY == y)
                || Math.abs(emptyY - y) == 1 && emptyX == x) {
            buttons[emptyX][emptyY].setText(button.getText().toString());
            buttons[emptyX][emptyY].setBackgroundColor(resources.getColor(R.color.purple_500));
            button.setText("");
            button.setBackgroundColor(Color.WHITE);
            emptyX = x;
            emptyY = y;
            checkWin();
        }
    }

    private void checkWin() {
        boolean isWin = false;
        if (emptyX == 3 && emptyY == 3) {
            for (int i = 0; i < (group.getChildCount() - 1); i++) {
                if (buttons[i / 4][i % 4].getText().toString().equals(String.valueOf(i + 1))) {
                    isWin = true;
                } else {
                    isWin = false;
                    break;
                }
            }
        }

        if (isWin) {
            Toast.makeText(this, "You Win!!!", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < group.getChildCount(); i++) {
                buttons[i / 4][i % 4].setClickable(false);
            }
        }
    }
}