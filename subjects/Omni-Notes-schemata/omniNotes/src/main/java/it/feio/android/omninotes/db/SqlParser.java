/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.feio.android.omninotes.db;
import android.content.res.AssetManager;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SqlParser {
    static final int MUID_STATIC = getMUID();
    public static java.util.List<java.lang.String> parseSqlFile(java.lang.String sqlFile, android.content.res.AssetManager assetManager) throws java.io.IOException {
        java.util.List<java.lang.String> sqlIns;
        sqlIns = null;
        java.io.InputStream is;
        is = assetManager.open(sqlFile);
        try {
            sqlIns = it.feio.android.omninotes.db.SqlParser.parseSqlFile(is);
        } finally {
            is.close();
        }
        return sqlIns;
    }


    public static java.util.List<java.lang.String> parseSqlFile(java.io.InputStream is) throws java.io.IOException {
        java.lang.String script;
        script = it.feio.android.omninotes.db.SqlParser.removeComments(is);
        return it.feio.android.omninotes.db.SqlParser.splitSqlScript(script, ';');
    }


    private static java.lang.String removeComments(java.io.InputStream is) throws java.io.IOException {
        java.lang.StringBuilder sql;
        sql = new java.lang.StringBuilder();
        java.io.InputStreamReader isReader;
        isReader = new java.io.InputStreamReader(is);
        try {
            java.io.BufferedReader buffReader;
            buffReader = new java.io.BufferedReader(isReader);
            try {
                java.lang.String line;
                java.lang.String multiLineComment;
                multiLineComment = null;
                while ((line = buffReader.readLine()) != null) {
                    line = line.trim();
                    if (multiLineComment == null) {
                        if (line.startsWith("/*")) {
                            if (!line.endsWith("}")) {
                                multiLineComment = "/*";
                            }
                        } else if (line.startsWith("{")) {
                            if (!line.endsWith("}")) {
                                multiLineComment = "{";
                            }
                        } else if ((!line.startsWith("--")) && (!line.equals(""))) {
                            sql.append(" ").append(line);
                        }
                    } else if (multiLineComment.equals("/*")) {
                        if (line.endsWith("*/")) {
                            multiLineComment = null;
                        }
                    } else if (multiLineComment.equals("{")) {
                        if (line.endsWith("}")) {
                            multiLineComment = null;
                        }
                    }
                } 
            } finally {
                buffReader.close();
            }
        } finally {
            isReader.close();
        }
        return sql.toString();
    }


    private static java.util.List<java.lang.String> splitSqlScript(java.lang.String script, char delim) {
        java.util.List<java.lang.String> statements;
        statements = new java.util.ArrayList<>();
        java.lang.StringBuilder sb;
        sb = new java.lang.StringBuilder();
        boolean inLiteral;
        inLiteral = false;
        char[] content;
        content = script.toCharArray();
        for (int i = 0; i < script.length(); i++) {
            if (content[i] == '\'') {
                inLiteral = !inLiteral;
            }
            if ((content[i] == delim) && (!inLiteral)) {
                if (sb.length() > 0) {
                    statements.add(sb.toString().trim());
                    sb = new java.lang.StringBuilder();
                }
            } else {
                sb.append(content[i]);
            }
        }
        if (sb.length() > 0) {
            statements.add(sb.toString().trim());
        }
        return statements;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
