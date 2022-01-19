package com.wizard.lua.enc;

import android.util.Log;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Lua {
    public static final Charset[] ENCODINGS = new Charset[]{
            StandardCharsets.UTF_8,
            StandardCharsets.UTF_16,
            StandardCharsets.US_ASCII
    };
    private Lua(){

    }

    public static String encrypt(String _password, String _script){
        if(_password.length() == 0 || _script.length() == 0)
            throw new IllegalArgumentException();
        int[] password = new int[_password.length()];
        int[] script = new int[_script.length()];
        for (int i = 0; i < password.length; i++)
            password[i] = _password.codePointAt(i);
        for (int i = 0; i < script.length; i++)
            script[i] = _script.codePointAt(i);

        int loop = Math.floorMod(script.length, password.length) + 1;
        int index = 0;
        for (int i = 0; i < loop; i++){
            for (int j : password) {
                if (index >= script.length)
                    break;
                script[index] ^= j;
                index++;
            }
        }
        StringBuilder result = new StringBuilder();
        result.append("b={");
        for(int i: script)
            result.append(i).append(",");
        result.setLength(result.length() - 1);
        result.append("};").append("\n");
        result.append("p={};").append("\n");
        result.append("pw=gg.prompt({[1] = 'Input the password'},{[1] = '0'})[1];").append("\n");
        result.append("for i = 1, #pw do").append("\n");
        result.append("    p[i] = string.byte(pw:sub(i,i), 1,-1);").append("\n");
        result.append("end").append("\n");
        result.append("loop=math.fmod(#b,#p)+1;").append("\n");
        result.append("i=1;").append("\n");
        result.append("for j = 1,loop,1 do").append("\n");
        result.append("    for l = 1,#p,1 do ").append("\n");
        result.append("        if i > #b then").append("\n");
        result.append("            break").append("\n");
        result.append("        end").append("\n");
        result.append("        b[i] = bit32.bxor(b[i],p[l]);").append("\n");
        result.append("        i = i + 1;").append("\n");
        result.append("    end").append("\n");
        result.append("end").append("\n");
        result.append("c = {};").append("\n");
        result.append("for _,v in ipairs(b) do").append("\n");
        result.append("    table.insert(c,utf8.char(v));").append("\n");
        result.append("end").append("\n");
        result.append("script = load(table.concat(c));").append("\n");
        result.append("if script == nil then").append("\n");
        result.append("    print('Password incorrect or the script is corrupted')").append("\n");
        result.append("else script() end");

        return result.toString();
    }
}
