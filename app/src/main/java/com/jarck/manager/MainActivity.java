
package com.jarck.manager;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<String> codes = new ArrayList<>();
    private ArrayList<String> statuses = new ArrayList<>();
    private LinearLayout listLayout;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadCodes();
        createInterface();
    }

    private void createInterface() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 24, 24, 24);
        root.setBackgroundColor(Color.rgb(18, 18, 18));

        TextView title = new TextView(this);
        title.setText("JARCK 1.0");
        title.setTextSize(28);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("Administrador de códigos");
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setGravity(Gravity.CENTER);
        root.addView(subtitle);

        input = new EditText(this);
        input.setHint("Pega tus códigos, uno por línea");
        input.setTextColor(Color.WHITE);
        input.setHintTextColor(Color.GRAY);
        root.addView(input);

        Button addButton = new Button(this);
        addButton.setText("GUARDAR CÓDIGOS");
        root.addView(addButton);

        addButton.setOnClickListener(v -> {
            String[] lines = input.getText().toString().split("\\n");
            int added = 0;

            for (String line : lines) {
                String code = line.trim();
                if (!code.isEmpty() && !codes.contains(code)) {
                    codes.add(code);
                    statuses.add("Pendiente");
                    added++;
                }
            }

            saveCodes();
            input.setText("");
            refreshList();
            Toast.makeText(this, added + " códigos guardados", Toast.LENGTH_SHORT).show();
        });

        Button redeemButton = new Button(this);
        redeemButton.setText("ABRIR MICROSOFT REDEEM");
        root.addView(redeemButton);

        redeemButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://redeem.microsoft.com/"));
            startActivity(intent);
        });

        TextView listTitle = new TextView(this);
        listTitle.setText("MIS CÓDIGOS");
        listTitle.setTextColor(Color.WHITE);
        listTitle.setTextSize(20);
        listTitle.setPadding(0, 20, 0, 10);
        root.addView(listTitle);

        listLayout = new LinearLayout(this);
        listLayout.setOrientation(LinearLayout.VERTICAL);

        android.widget.ScrollView scroll = new android.widget.ScrollView(this);
        scroll.addView(listLayout);
        root.addView(scroll, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));

        setContentView(root);
        refreshList();
    }

    private void refreshList() {
        listLayout.removeAllViews();

        for (int i = 0; i < codes.size(); i++) {
            final int position = i;

            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setPadding(12, 12, 12, 12);
            item.setBackgroundColor(Color.rgb(38, 38, 38));

            TextView codeText = new TextView(this);
            codeText.setText(codes.get(i));
            codeText.setTextColor(Color.WHITE);
            codeText.setTextSize(16);
            item.addView(codeText);

            TextView statusText = new TextView(this);
            statusText.setText("Estado: " + statuses.get(i));
            statusText.setTextColor(Color.rgb(160, 220, 100));
            item.addView(statusText);

            Button copyButton = new Button(this);
            copyButton.setText("COPIAR CÓDIGO");
            item.addView(copyButton);

            copyButton.setOnClickListener(v -> {
                ClipboardManager clipboard =
                        (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Código", codes.get(position));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Código copiado", Toast.LENGTH_SHORT).show();
            });

            Button statusButton = new Button(this);
            statusButton.setText("CAMBIAR ESTADO");
            item.addView(statusButton);

            statusButton.setOnClickListener(v -> {
                String current = statuses.get(position);

                if (current.equals("Pendiente")) {
                    statuses.set(position, "Revisado");
                } else if (current.equals("Revisado")) {
                    statuses.set(position, "Canjeado");
                } else {
                    statuses.set(position, "Pendiente");
                }

                saveCodes();
                refreshList();
            });

            listLayout.addView(item);

            View separator = new View(this);
            separator.setBackgroundColor(Color.DKGRAY);
            listLayout.addView(separator, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 2));
        }
    }

    private void saveCodes() {
        SharedPreferences preferences =
                getSharedPreferences("JARCK_DATA", MODE_PRIVATE);

        preferences.edit()
                .putString("codes", android.text.TextUtils.join("|||", codes))
                .putString("statuses", android.text.TextUtils.join("|||", statuses))
                .apply();
    }

    private void loadCodes() {
        SharedPreferences preferences =
                getSharedPreferences("JARCK_DATA", MODE_PRIVATE);

        String savedCodes = preferences.getString("codes", "");
        String savedStatuses = preferences.getString("statuses", "");

        if (!savedCodes.isEmpty()) {
            String[] codeArray = savedCodes.split("\\|\\|\\|");
            String[] statusArray = savedStatuses.isEmpty()
                    ? new String[0] : savedStatuses.split("\\|\\|\\|");

            for (int i = 0; i < codeArray.length; i++) {
                codes.add(codeArray[i]);
                statuses.add(i < statusArray.length
                        ? statusArray[i] : "Pendiente");
            }
        }
    }
}
