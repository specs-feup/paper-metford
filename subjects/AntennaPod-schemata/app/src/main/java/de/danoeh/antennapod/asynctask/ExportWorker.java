package de.danoeh.antennapod.asynctask;
import de.danoeh.antennapod.core.export.ExportWriter;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import android.util.Log;
import java.io.FileOutputStream;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.core.storage.DBReader;
import java.io.IOException;
import java.nio.charset.Charset;
import io.reactivex.Observable;
import java.io.OutputStreamWriter;
import java.io.File;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Writes an OPML file into the export directory in the background.
 */
public class ExportWorker {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String EXPORT_DIR = "export/";

    private static final java.lang.String TAG = "ExportWorker";

    private static final java.lang.String DEFAULT_OUTPUT_NAME = "antennapod-feeds";

    @androidx.annotation.NonNull
    private final de.danoeh.antennapod.core.export.ExportWriter exportWriter;

    @androidx.annotation.NonNull
    private final java.io.File output;

    private final android.content.Context context;

    public ExportWorker(@androidx.annotation.NonNull
    de.danoeh.antennapod.core.export.ExportWriter exportWriter, android.content.Context context) {
        this(exportWriter, new java.io.File(de.danoeh.antennapod.storage.preferences.UserPreferences.getDataFolder(de.danoeh.antennapod.asynctask.ExportWorker.EXPORT_DIR), (de.danoeh.antennapod.asynctask.ExportWorker.DEFAULT_OUTPUT_NAME + ".") + exportWriter.fileExtension()), context);
    }


    private ExportWorker(@androidx.annotation.NonNull
    de.danoeh.antennapod.core.export.ExportWriter exportWriter, @androidx.annotation.NonNull
    java.io.File output, android.content.Context context) {
        this.exportWriter = exportWriter;
        this.output = output;
        this.context = context;
    }


    public io.reactivex.Observable<java.io.File> exportObservable() {
        if (output.exists()) {
            boolean success;
            success = output.delete();
            android.util.Log.w(de.danoeh.antennapod.asynctask.ExportWorker.TAG, "Overwriting previously exported file: " + success);
        }
        return io.reactivex.Observable.create((io.reactivex.ObservableEmitter<java.io.File> subscriber) -> {
            java.io.OutputStreamWriter writer;
            writer = null;
            try {
                writer = new java.io.OutputStreamWriter(new java.io.FileOutputStream(output), java.nio.charset.Charset.forName("UTF-8"));
                exportWriter.writeDocument(de.danoeh.antennapod.core.storage.DBReader.getFeedList(), writer, context);
                subscriber.onNext(output);
            } catch (java.io.IOException e) {
                subscriber.onError(e);
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (java.io.IOException e) {
                        subscriber.onError(e);
                    }
                }
                subscriber.onComplete();
            }
        });
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
