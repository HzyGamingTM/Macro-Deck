package com.example.streamdecktest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.net.Socket


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_menu)

        findViewById<Button>(R.id.start_button).setOnClickListener{
            val ip: String = findViewById<TextView>(R.id.address_field).text.toString();
            val port: Int = findViewById<TextView>(R.id.port_field).text.toString().toInt();

            Log.e("asd", "asd");

            // TODO: Create socket client
            Thread {
                Connection().startConnection(ip, port);
            }.start();
        }
    }
}

class Connection {
    fun addressValidity(address: String, port: Int): Boolean {
        if (port < 1 || port > 65535) return false;

        var numcount = 0;
        var dotcount = 0;
        var numberupdated = false;
        var dotcountupdated = true;
        for (char: Char in address) {
            val ascii = char.code;
            Log.e("addr", ascii.toString());
            if (ascii in 48..57) {
                if (!numberupdated) numcount += 1;
                    numberupdated = true;
            } else if (ascii == 46) {
                if (dotcountupdated) return false;
                dotcount += 1;
            } else return false;
            dotcountupdated = false;
            if (numcount > 4) return false;
            if (dotcount > 3) return false;
        }

        val addressArray: List<String> = address.split(".");
        for (string in addressArray) {
            if (string.toInt() > 255) {
                return false;
            }
        }

        return true;
    }

    fun startConnection(ip: String, port: Int) {
        if (addressValidity(address=ip, port=port)) {
            Log.e("asd", "Before Saluttion");
            val client = Socket(ip, port);
            Log.e("asd", "Saluttion");
            client.getOutputStream().write("Salutations Environment".toByteArray());
            client.close();
        } else Log.e("asd", "Invalid port (trash)")
    }
}

/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StreamDeckTestTheme {
        Greeting("Android")
    }
}
*/