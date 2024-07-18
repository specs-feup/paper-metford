package com.phk.androidmicrobenchmark;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Benchmark {

    private final static int ITERATIONS = 10_000_000;
    private final static int MUID_FINAL_INT = getIntProp(); // propertyValue = 1
    private static int MUID_NON_FINAL_INT = getIntProp(); // propertyValue = 1
    private final static String MUID_FINAL_STRING = getStringProp(); // propertyValue = "ae7f9682-6bd8-45e8-a3da-4020ccc153fa"

    public static void main(String[] args) {
        Benchmark.testHarness("static non-final int, property, switch", Benchmark::nonFinalMutatedFirst);
        Benchmark.testHarness("static non-final int, property, default", Benchmark::nonFinalMutatedDefault);
        Benchmark.testHarness("static final int, property, first if path", Benchmark::ifElseMutatedFirstIf);
        Benchmark.testHarness("static final int, property, else path", Benchmark::ifElseMutatedDefault);
        Benchmark.testHarness("static final String, property, switch", Benchmark::stringMutatedFirst);
        Benchmark.testHarness("static final String, property, default", Benchmark::stringMutatedDefault);
        Benchmark.testHarness("static final int, property, switch", Benchmark::switchMutatedFirst);
        Benchmark.testHarness("static final int, property, default", Benchmark::switchMutatedDefault);
    }

    private static int getIntProp() {
        return Integer.parseInt(getProp("debug.INT_MUID"));
    }

    private static String getStringProp() {
        return getProp("debug.STRING_MUID");
    }

    private static String getProp(String propId) {
        String propertyValue = null;
        try {
            java.lang.Process process = Runtime.getRuntime().exec("getprop " + propId);
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            propertyValue = reader.readLine();
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return propertyValue;
    }

    public static long testHarness(String name, Runnable experiment) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            experiment.run();
        }
        long stop = System.nanoTime();
        long duration = stop - start;

        Log.d("BENCHMARK: ", "Experiment '" + name + "' took " + (duration) / 1_000_000 + "ms");
        return duration;
    }

    public static void switchMutatedFirst() {
        long count = 0;
        switch (MUID_FINAL_INT) {
            case 1:
                count += 1;
                break;
            case 2:
                count += 2;
                break;
            case 3:
                count += 3;
                break;
            case 4:
                count += 4;
                break;
            case 5:
                count += 5;
                break;
            case 6:
                count += 6;
                break;
            case 7:
                count += 7;
                break;
            case 8:
                count += 8;
                break;
            case 9:
                count += 9;
                break;
            case 10:
                count += 10;
                break;
            case 11:
                count += 11;
                break;
            case 12:
                count += 12;
                break;
            case 13:
                count += 13;
                break;
            case 14:
                count += 14;
                break;
            case 15:
                count += 15;
                break;
            case 16:
                count += 16;
                break;
            case 17:
                count += 17;
                break;
            case 18:
                count += 18;
                break;
            case 19:
                count += 19;
                break;
            case 20:
                count += 20;
                break;
            case 21:
                count += 21;
                break;
            case 22:
                count += 22;
                break;
            case 23:
                count += 23;
                break;
            case 24:
                count += 24;
                break;
            case 25:
                count += 25;
                break;
            case 26:
                count += 26;
                break;
            case 27:
                count += 27;
                break;
            case 28:
                count += 28;
                break;
            case 29:
                count += 29;
                break;
            case 30:
                count += 30;
                break;
            case 31:
                count += 31;
                break;
            case 32:
                count += 32;
                break;
            case 33:
                count += 33;
                break;
            case 34:
                count += 34;
                break;
            case 35:
                count += 35;
                break;
            case 36:
                count += 36;
                break;
            case 37:
                count += 37;
                break;
            case 38:
                count += 38;
                break;
            case 39:
                count += 39;
                break;
            case 40:
                count += 40;
                break;
            case 41:
                count += 41;
                break;
            case 42:
                count += 42;
                break;
            case 43:
                count += 43;
                break;
            case 44:
                count += 44;
                break;
            case 45:
                count += 45;
                break;
            case 46:
                count += 46;
                break;
            case 47:
                count += 47;
                break;
            case 48:
                count += 48;
                break;
            case 49:
                count += 49;
                break;
            case 50:
                count += 50;
                break;
            case 51:
                count += 51;
                break;
            case 52:
                count += 52;
                break;
            case 53:
                count += 53;
                break;
            case 54:
                count += 54;
                break;
            case 55:
                count += 55;
                break;
            case 56:
                count += 56;
                break;
            case 57:
                count += 57;
                break;
            case 58:
                count += 58;
                break;
            case 59:
                count += 59;
                break;
            case 60:
                count += 60;
                break;
            case 61:
                count += 61;
                break;
            case 62:
                count += 62;
                break;
            case 63:
                count += 63;
                break;
            case 64:
                count += 64;
                break;
            case 65:
                count += 65;
                break;
            case 66:
                count += 66;
                break;
            case 67:
                count += 67;
                break;
            case 68:
                count += 68;
                break;
            case 69:
                count += 69;
                break;
            case 70:
                count += 70;
                break;
            case 71:
                count += 71;
                break;
            case 72:
                count += 72;
                break;
            case 73:
                count += 73;
                break;
            case 74:
                count += 74;
                break;
            case 75:
                count += 75;
                break;
            case 76:
                count += 76;
                break;
            case 77:
                count += 77;
                break;
            case 78:
                count += 78;
                break;
            case 79:
                count += 79;
                break;
            case 80:
                count += 80;
                break;
            case 81:
                count += 81;
                break;
            case 82:
                count += 82;
                break;
            case 83:
                count += 83;
                break;
            case 84:
                count += 84;
                break;
            case 85:
                count += 85;
                break;
            case 86:
                count += 86;
                break;
            case 87:
                count += 87;
                break;
            case 88:
                count += 88;
                break;
            case 89:
                count += 89;
                break;
            case 90:
                count += 90;
                break;
            case 91:
                count += 91;
                break;
            case 92:
                count += 92;
                break;
            case 93:
                count += 93;
                break;
            case 94:
                count += 94;
                break;
            case 95:
                count += 95;
                break;
            case 96:
                count += 96;
                break;
            case 97:
                count += 97;
                break;
            case 98:
                count += 98;
                break;
            case 99:
                count += 99;
                break;
            case 100:
                count += 100;
                break;
            default:
                count += 1000;
        }
    }

    // switch mutated default
    public static void switchMutatedDefault() {
        long count = 0;
        switch (MUID_FINAL_INT) {
            case -1:
                count += 1;
                break;
            case 2:
                count += 2;
                break;
            case 3:
                count += 3;
                break;
            case 4:
                count += 4;
                break;
            case 5:
                count += 5;
                break;
            case 6:
                count += 6;
                break;
            case 7:
                count += 7;
                break;
            case 8:
                count += 8;
                break;
            case 9:
                count += 9;
                break;
            case 10:
                count += 10;
                break;
            case 11:
                count += 11;
                break;
            case 12:
                count += 12;
                break;
            case 13:
                count += 13;
                break;
            case 14:
                count += 14;
                break;
            case 15:
                count += 15;
                break;
            case 16:
                count += 16;
                break;
            case 17:
                count += 17;
                break;
            case 18:
                count += 18;
                break;
            case 19:
                count += 19;
                break;
            case 20:
                count += 20;
                break;
            case 21:
                count += 21;
                break;
            case 22:
                count += 22;
                break;
            case 23:
                count += 23;
                break;
            case 24:
                count += 24;
                break;
            case 25:
                count += 25;
                break;
            case 26:
                count += 26;
                break;
            case 27:
                count += 27;
                break;
            case 28:
                count += 28;
                break;
            case 29:
                count += 29;
                break;
            case 30:
                count += 30;
                break;
            case 31:
                count += 31;
                break;
            case 32:
                count += 32;
                break;
            case 33:
                count += 33;
                break;
            case 34:
                count += 34;
                break;
            case 35:
                count += 35;
                break;
            case 36:
                count += 36;
                break;
            case 37:
                count += 37;
                break;
            case 38:
                count += 38;
                break;
            case 39:
                count += 39;
                break;
            case 40:
                count += 40;
                break;
            case 41:
                count += 41;
                break;
            case 42:
                count += 42;
                break;
            case 43:
                count += 43;
                break;
            case 44:
                count += 44;
                break;
            case 45:
                count += 45;
                break;
            case 46:
                count += 46;
                break;
            case 47:
                count += 47;
                break;
            case 48:
                count += 48;
                break;
            case 49:
                count += 49;
                break;
            case 50:
                count += 50;
                break;
            case 51:
                count += 51;
                break;
            case 52:
                count += 52;
                break;
            case 53:
                count += 53;
                break;
            case 54:
                count += 54;
                break;
            case 55:
                count += 55;
                break;
            case 56:
                count += 56;
                break;
            case 57:
                count += 57;
                break;
            case 58:
                count += 58;
                break;
            case 59:
                count += 59;
                break;
            case 60:
                count += 60;
                break;
            case 61:
                count += 61;
                break;
            case 62:
                count += 62;
                break;
            case 63:
                count += 63;
                break;
            case 64:
                count += 64;
                break;
            case 65:
                count += 65;
                break;
            case 66:
                count += 66;
                break;
            case 67:
                count += 67;
                break;
            case 68:
                count += 68;
                break;
            case 69:
                count += 69;
                break;
            case 70:
                count += 70;
                break;
            case 71:
                count += 71;
                break;
            case 72:
                count += 72;
                break;
            case 73:
                count += 73;
                break;
            case 74:
                count += 74;
                break;
            case 75:
                count += 75;
                break;
            case 76:
                count += 76;
                break;
            case 77:
                count += 77;
                break;
            case 78:
                count += 78;
                break;
            case 79:
                count += 79;
                break;
            case 80:
                count += 80;
                break;
            case 81:
                count += 81;
                break;
            case 82:
                count += 82;
                break;
            case 83:
                count += 83;
                break;
            case 84:
                count += 84;
                break;
            case 85:
                count += 85;
                break;
            case 86:
                count += 86;
                break;
            case 87:
                count += 87;
                break;
            case 88:
                count += 88;
                break;
            case 89:
                count += 89;
                break;
            case 90:
                count += 90;
                break;
            case 91:
                count += 91;
                break;
            case 92:
                count += 92;
                break;
            case 93:
                count += 93;
                break;
            case 94:
                count += 94;
                break;
            case 95:
                count += 95;
                break;
            case 96:
                count += 96;
                break;
            case 97:
                count += 97;
                break;
            case 98:
                count += 98;
                break;
            case 99:
                count += 99;
                break;
            case 100:
                count += 100;
                break;
            default:
                count += 1000;
        }
    }

    public static void ifElseMutatedFirstIf() {
        long count = 0;
        int MUID = MUID_FINAL_INT;
        if (MUID == 1) {
            count += 1;
        } else if (MUID == 2) {
            count += 2;
        } else if (MUID == 3) {
            count += 3;
        } else if (MUID == 4) {
            count += 4;
        } else if (MUID == 5) {
            count += 5;
        } else if (MUID == 6) {
            count += 6;
        } else if (MUID == 7) {
            count += 7;
        } else if (MUID == 8) {
            count += 8;
        } else if (MUID == 9) {
            count += 9;
        } else if (MUID == 10) {
            count += 10;
        } else if (MUID == 11) {
            count += 11;
        } else if (MUID == 12) {
            count += 12;
        } else if (MUID == 13) {
            count += 13;
        } else if (MUID == 14) {
            count += 14;
        } else if (MUID == 15) {
            count += 15;
        } else if (MUID == 16) {
            count += 16;
        } else if (MUID == 17) {
            count += 17;
        } else if (MUID == 18) {
            count += 18;
        } else if (MUID == 19) {
            count += 19;
        } else if (MUID == 20) {
            count += 20;
        } else if (MUID == 21) {
            count += 21;
        } else if (MUID == 22) {
            count += 22;
        } else if (MUID == 23) {
            count += 23;
        } else if (MUID == 24) {
            count += 24;
        } else if (MUID == 25) {
            count += 25;
        } else if (MUID == 26) {
            count += 26;
        } else if (MUID == 27) {
            count += 27;
        } else if (MUID == 28) {
            count += 28;
        } else if (MUID == 29) {
            count += 29;
        } else if (MUID == 30) {
            count += 30;
        } else if (MUID == 31) {
            count += 31;
        } else if (MUID == 32) {
            count += 32;
        } else if (MUID == 33) {
            count += 33;
        } else if (MUID == 34) {
            count += 34;
        } else if (MUID == 35) {
            count += 35;
        } else if (MUID == 36) {
            count += 36;
        } else if (MUID == 37) {
            count += 37;
        } else if (MUID == 38) {
            count += 38;
        } else if (MUID == 39) {
            count += 39;
        } else if (MUID == 40) {
            count += 40;
        } else if (MUID == 41) {
            count += 41;
        } else if (MUID == 42) {
            count += 42;
        } else if (MUID == 43) {
            count += 43;
        } else if (MUID == 44) {
            count += 44;
        } else if (MUID == 45) {
            count += 45;
        } else if (MUID == 46) {
            count += 46;
        } else if (MUID == 47) {
            count += 47;
        } else if (MUID == 48) {
            count += 48;
        } else if (MUID == 49) {
            count += 49;
        } else if (MUID == 50) {
            count += 50;
        } else if (MUID == 51) {
            count += 51;
        } else if (MUID == 52) {
            count += 52;
        } else if (MUID == 53) {
            count += 53;
        } else if (MUID == 54) {
            count += 54;
        } else if (MUID == 55) {
            count += 55;
        } else if (MUID == 56) {
            count += 56;
        } else if (MUID == 57) {
            count += 57;
        } else if (MUID == 58) {
            count += 58;
        } else if (MUID == 59) {
            count += 59;
        } else if (MUID == 60) {
            count += 60;
        } else if (MUID == 61) {
            count += 61;
        } else if (MUID == 62) {
            count += 62;
        } else if (MUID == 63) {
            count += 63;
        } else if (MUID == 64) {
            count += 64;
        } else if (MUID == 65) {
            count += 65;
        } else if (MUID == 66) {
            count += 66;
        } else if (MUID == 67) {
            count += 67;
        } else if (MUID == 68) {
            count += 68;
        } else if (MUID == 69) {
            count += 69;
        } else if (MUID == 70) {
            count += 70;
        } else if (MUID == 71) {
            count += 71;
        } else if (MUID == 72) {
            count += 72;
        } else if (MUID == 73) {
            count += 73;
        } else if (MUID == 74) {
            count += 74;
        } else if (MUID == 75) {
            count += 75;
        } else if (MUID == 76) {
            count += 76;
        } else if (MUID == 77) {
            count += 77;
        } else if (MUID == 78) {
            count += 78;
        } else if (MUID == 79) {
            count += 79;
        } else if (MUID == 80) {
            count += 80;
        } else if (MUID == 81) {
            count += 81;
        } else if (MUID == 82) {
            count += 82;
        } else if (MUID == 83) {
            count += 83;
        } else if (MUID == 84) {
            count += 84;
        } else if (MUID == 85) {
            count += 85;
        } else if (MUID == 86) {
            count += 86;
        } else if (MUID == 87) {
            count += 87;
        } else if (MUID == 88) {
            count += 88;
        } else if (MUID == 89) {
            count += 89;
        } else if (MUID == 90) {
            count += 90;
        } else if (MUID == 91) {
            count += 91;
        } else if (MUID == 92) {
            count += 92;
        } else if (MUID == 93) {
            count += 93;
        } else if (MUID == 94) {
            count += 94;
        } else if (MUID == 95) {
            count += 95;
        } else if (MUID == 96) {
            count += 96;
        } else if (MUID == 97) {
            count += 97;
        } else if (MUID == 98) {
            count += 98;
        } else if (MUID == 99) {
            count += 99;
        } else if (MUID == 100) {
            count += 100;
        } else {
            count += 1000;
        }
    }

    public static void ifElseMutatedDefault() {
        long count = 0;
        int MUID = MUID_FINAL_INT;
        if (MUID == -1) {
            count += 1;
        } else if (MUID == 2) {
            count += 2;
        } else if (MUID == 3) {
            count += 3;
        } else if (MUID == 4) {
            count += 4;
        } else if (MUID == 5) {
            count += 5;
        } else if (MUID == 6) {
            count += 6;
        } else if (MUID == 7) {
            count += 7;
        } else if (MUID == 8) {
            count += 8;
        } else if (MUID == 9) {
            count += 9;
        } else if (MUID == 10) {
            count += 10;
        } else if (MUID == 11) {
            count += 11;
        } else if (MUID == 12) {
            count += 12;
        } else if (MUID == 13) {
            count += 13;
        } else if (MUID == 14) {
            count += 14;
        } else if (MUID == 15) {
            count += 15;
        } else if (MUID == 16) {
            count += 16;
        } else if (MUID == 17) {
            count += 17;
        } else if (MUID == 18) {
            count += 18;
        } else if (MUID == 19) {
            count += 19;
        } else if (MUID == 20) {
            count += 20;
        } else if (MUID == 21) {
            count += 21;
        } else if (MUID == 22) {
            count += 22;
        } else if (MUID == 23) {
            count += 23;
        } else if (MUID == 24) {
            count += 24;
        } else if (MUID == 25) {
            count += 25;
        } else if (MUID == 26) {
            count += 26;
        } else if (MUID == 27) {
            count += 27;
        } else if (MUID == 28) {
            count += 28;
        } else if (MUID == 29) {
            count += 29;
        } else if (MUID == 30) {
            count += 30;
        } else if (MUID == 31) {
            count += 31;
        } else if (MUID == 32) {
            count += 32;
        } else if (MUID == 33) {
            count += 33;
        } else if (MUID == 34) {
            count += 34;
        } else if (MUID == 35) {
            count += 35;
        } else if (MUID == 36) {
            count += 36;
        } else if (MUID == 37) {
            count += 37;
        } else if (MUID == 38) {
            count += 38;
        } else if (MUID == 39) {
            count += 39;
        } else if (MUID == 40) {
            count += 40;
        } else if (MUID == 41) {
            count += 41;
        } else if (MUID == 42) {
            count += 42;
        } else if (MUID == 43) {
            count += 43;
        } else if (MUID == 44) {
            count += 44;
        } else if (MUID == 45) {
            count += 45;
        } else if (MUID == 46) {
            count += 46;
        } else if (MUID == 47) {
            count += 47;
        } else if (MUID == 48) {
            count += 48;
        } else if (MUID == 49) {
            count += 49;
        } else if (MUID == 50) {
            count += 50;
        } else if (MUID == 51) {
            count += 51;
        } else if (MUID == 52) {
            count += 52;
        } else if (MUID == 53) {
            count += 53;
        } else if (MUID == 54) {
            count += 54;
        } else if (MUID == 55) {
            count += 55;
        } else if (MUID == 56) {
            count += 56;
        } else if (MUID == 57) {
            count += 57;
        } else if (MUID == 58) {
            count += 58;
        } else if (MUID == 59) {
            count += 59;
        } else if (MUID == 60) {
            count += 60;
        } else if (MUID == 61) {
            count += 61;
        } else if (MUID == 62) {
            count += 62;
        } else if (MUID == 63) {
            count += 63;
        } else if (MUID == 64) {
            count += 64;
        } else if (MUID == 65) {
            count += 65;
        } else if (MUID == 66) {
            count += 66;
        } else if (MUID == 67) {
            count += 67;
        } else if (MUID == 68) {
            count += 68;
        } else if (MUID == 69) {
            count += 69;
        } else if (MUID == 70) {
            count += 70;
        } else if (MUID == 71) {
            count += 71;
        } else if (MUID == 72) {
            count += 72;
        } else if (MUID == 73) {
            count += 73;
        } else if (MUID == 74) {
            count += 74;
        } else if (MUID == 75) {
            count += 75;
        } else if (MUID == 76) {
            count += 76;
        } else if (MUID == 77) {
            count += 77;
        } else if (MUID == 78) {
            count += 78;
        } else if (MUID == 79) {
            count += 79;
        } else if (MUID == 80) {
            count += 80;
        } else if (MUID == 81) {
            count += 81;
        } else if (MUID == 82) {
            count += 82;
        } else if (MUID == 83) {
            count += 83;
        } else if (MUID == 84) {
            count += 84;
        } else if (MUID == 85) {
            count += 85;
        } else if (MUID == 86) {
            count += 86;
        } else if (MUID == 87) {
            count += 87;
        } else if (MUID == 88) {
            count += 88;
        } else if (MUID == 89) {
            count += 89;
        } else if (MUID == 90) {
            count += 90;
        } else if (MUID == 91) {
            count += 91;
        } else if (MUID == 92) {
            count += 92;
        } else if (MUID == 93) {
            count += 93;
        } else if (MUID == 94) {
            count += 94;
        } else if (MUID == 95) {
            count += 95;
        } else if (MUID == 96) {
            count += 96;
        } else if (MUID == 97) {
            count += 97;
        } else if (MUID == 98) {
            count += 98;
        } else if (MUID == 99) {
            count += 99;
        } else if (MUID == 100) {
            count += 100;
        } else {
            count += 1000;
        }

    }

    public static void nonFinalMutatedFirst() {
        long count = 0;
        switch (MUID_NON_FINAL_INT) {
            case 1:
                count += 1;
                break;
            case 2:
                count += 2;
                break;
            case 3:
                count += 3;
                break;
            case 4:
                count += 4;
                break;
            case 5:
                count += 5;
                break;
            case 6:
                count += 6;
                break;
            case 7:
                count += 7;
                break;
            case 8:
                count += 8;
                break;
            case 9:
                count += 9;
                break;
            case 10:
                count += 10;
                break;
            case 11:
                count += 11;
                break;
            case 12:
                count += 12;
                break;
            case 13:
                count += 13;
                break;
            case 14:
                count += 14;
                break;
            case 15:
                count += 15;
                break;
            case 16:
                count += 16;
                break;
            case 17:
                count += 17;
                break;
            case 18:
                count += 18;
                break;
            case 19:
                count += 19;
                break;
            case 20:
                count += 20;
                break;
            case 21:
                count += 21;
                break;
            case 22:
                count += 22;
                break;
            case 23:
                count += 23;
                break;
            case 24:
                count += 24;
                break;
            case 25:
                count += 25;
                break;
            case 26:
                count += 26;
                break;
            case 27:
                count += 27;
                break;
            case 28:
                count += 28;
                break;
            case 29:
                count += 29;
                break;
            case 30:
                count += 30;
                break;
            case 31:
                count += 31;
                break;
            case 32:
                count += 32;
                break;
            case 33:
                count += 33;
                break;
            case 34:
                count += 34;
                break;
            case 35:
                count += 35;
                break;
            case 36:
                count += 36;
                break;
            case 37:
                count += 37;
                break;
            case 38:
                count += 38;
                break;
            case 39:
                count += 39;
                break;
            case 40:
                count += 40;
                break;
            case 41:
                count += 41;
                break;
            case 42:
                count += 42;
                break;
            case 43:
                count += 43;
                break;
            case 44:
                count += 44;
                break;
            case 45:
                count += 45;
                break;
            case 46:
                count += 46;
                break;
            case 47:
                count += 47;
                break;
            case 48:
                count += 48;
                break;
            case 49:
                count += 49;
                break;
            case 50:
                count += 50;
                break;
            case 51:
                count += 51;
                break;
            case 52:
                count += 52;
                break;
            case 53:
                count += 53;
                break;
            case 54:
                count += 54;
                break;
            case 55:
                count += 55;
                break;
            case 56:
                count += 56;
                break;
            case 57:
                count += 57;
                break;
            case 58:
                count += 58;
                break;
            case 59:
                count += 59;
                break;
            case 60:
                count += 60;
                break;
            case 61:
                count += 61;
                break;
            case 62:
                count += 62;
                break;
            case 63:
                count += 63;
                break;
            case 64:
                count += 64;
                break;
            case 65:
                count += 65;
                break;
            case 66:
                count += 66;
                break;
            case 67:
                count += 67;
                break;
            case 68:
                count += 68;
                break;
            case 69:
                count += 69;
                break;
            case 70:
                count += 70;
                break;
            case 71:
                count += 71;
                break;
            case 72:
                count += 72;
                break;
            case 73:
                count += 73;
                break;
            case 74:
                count += 74;
                break;
            case 75:
                count += 75;
                break;
            case 76:
                count += 76;
                break;
            case 77:
                count += 77;
                break;
            case 78:
                count += 78;
                break;
            case 79:
                count += 79;
                break;
            case 80:
                count += 80;
                break;
            case 81:
                count += 81;
                break;
            case 82:
                count += 82;
                break;
            case 83:
                count += 83;
                break;
            case 84:
                count += 84;
                break;
            case 85:
                count += 85;
                break;
            case 86:
                count += 86;
                break;
            case 87:
                count += 87;
                break;
            case 88:
                count += 88;
                break;
            case 89:
                count += 89;
                break;
            case 90:
                count += 90;
                break;
            case 91:
                count += 91;
                break;
            case 92:
                count += 92;
                break;
            case 93:
                count += 93;
                break;
            case 94:
                count += 94;
                break;
            case 95:
                count += 95;
                break;
            case 96:
                count += 96;
                break;
            case 97:
                count += 97;
                break;
            case 98:
                count += 98;
                break;
            case 99:
                count += 99;
                break;
            case 100:
                count += 100;
                break;
            default:
                count += 1000;
        }

    }

    public static void nonFinalMutatedDefault() {
        long count = 0;
        switch (MUID_NON_FINAL_INT) {
            case -1:
                count += 1;
                break;
            case 2:
                count += 2;
                break;
            case 3:
                count += 3;
                break;
            case 4:
                count += 4;
                break;
            case 5:
                count += 5;
                break;
            case 6:
                count += 6;
                break;
            case 7:
                count += 7;
                break;
            case 8:
                count += 8;
                break;
            case 9:
                count += 9;
                break;
            case 10:
                count += 10;
                break;
            case 11:
                count += 11;
                break;
            case 12:
                count += 12;
                break;
            case 13:
                count += 13;
                break;
            case 14:
                count += 14;
                break;
            case 15:
                count += 15;
                break;
            case 16:
                count += 16;
                break;
            case 17:
                count += 17;
                break;
            case 18:
                count += 18;
                break;
            case 19:
                count += 19;
                break;
            case 20:
                count += 20;
                break;
            case 21:
                count += 21;
                break;
            case 22:
                count += 22;
                break;
            case 23:
                count += 23;
                break;
            case 24:
                count += 24;
                break;
            case 25:
                count += 25;
                break;
            case 26:
                count += 26;
                break;
            case 27:
                count += 27;
                break;
            case 28:
                count += 28;
                break;
            case 29:
                count += 29;
                break;
            case 30:
                count += 30;
                break;
            case 31:
                count += 31;
                break;
            case 32:
                count += 32;
                break;
            case 33:
                count += 33;
                break;
            case 34:
                count += 34;
                break;
            case 35:
                count += 35;
                break;
            case 36:
                count += 36;
                break;
            case 37:
                count += 37;
                break;
            case 38:
                count += 38;
                break;
            case 39:
                count += 39;
                break;
            case 40:
                count += 40;
                break;
            case 41:
                count += 41;
                break;
            case 42:
                count += 42;
                break;
            case 43:
                count += 43;
                break;
            case 44:
                count += 44;
                break;
            case 45:
                count += 45;
                break;
            case 46:
                count += 46;
                break;
            case 47:
                count += 47;
                break;
            case 48:
                count += 48;
                break;
            case 49:
                count += 49;
                break;
            case 50:
                count += 50;
                break;
            case 51:
                count += 51;
                break;
            case 52:
                count += 52;
                break;
            case 53:
                count += 53;
                break;
            case 54:
                count += 54;
                break;
            case 55:
                count += 55;
                break;
            case 56:
                count += 56;
                break;
            case 57:
                count += 57;
                break;
            case 58:
                count += 58;
                break;
            case 59:
                count += 59;
                break;
            case 60:
                count += 60;
                break;
            case 61:
                count += 61;
                break;
            case 62:
                count += 62;
                break;
            case 63:
                count += 63;
                break;
            case 64:
                count += 64;
                break;
            case 65:
                count += 65;
                break;
            case 66:
                count += 66;
                break;
            case 67:
                count += 67;
                break;
            case 68:
                count += 68;
                break;
            case 69:
                count += 69;
                break;
            case 70:
                count += 70;
                break;
            case 71:
                count += 71;
                break;
            case 72:
                count += 72;
                break;
            case 73:
                count += 73;
                break;
            case 74:
                count += 74;
                break;
            case 75:
                count += 75;
                break;
            case 76:
                count += 76;
                break;
            case 77:
                count += 77;
                break;
            case 78:
                count += 78;
                break;
            case 79:
                count += 79;
                break;
            case 80:
                count += 80;
                break;
            case 81:
                count += 81;
                break;
            case 82:
                count += 82;
                break;
            case 83:
                count += 83;
                break;
            case 84:
                count += 84;
                break;
            case 85:
                count += 85;
                break;
            case 86:
                count += 86;
                break;
            case 87:
                count += 87;
                break;
            case 88:
                count += 88;
                break;
            case 89:
                count += 89;
                break;
            case 90:
                count += 90;
                break;
            case 91:
                count += 91;
                break;
            case 92:
                count += 92;
                break;
            case 93:
                count += 93;
                break;
            case 94:
                count += 94;
                break;
            case 95:
                count += 95;
                break;
            case 96:
                count += 96;
                break;
            case 97:
                count += 97;
                break;
            case 98:
                count += 98;
                break;
            case 99:
                count += 99;
                break;
            case 100:
                count += 100;
                break;
            default:
                count += 1000;
        }
    }

    public static void stringMutatedFirst() {
        long count = 0;
        switch (MUID_FINAL_STRING) {
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa":
                count += 1;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa1":
                count += 2;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa2":
                count += 3;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa3":
                count += 4;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa4":
                count += 5;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa5":
                count += 6;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa6":
                count += 7;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa7":
                count += 8;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa8":
                count += 9;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa9":
                count += 10;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa10":
                count += 11;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa11":
                count += 12;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa12":
                count += 13;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa13":
                count += 14;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa14":
                count += 15;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa15":
                count += 16;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa16":
                count += 17;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa17":
                count += 18;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa18":
                count += 19;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa19":
                count += 20;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa20":
                count += 21;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa21":
                count += 22;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa22":
                count += 23;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa23":
                count += 24;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa24":
                count += 25;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa25":
                count += 26;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa26":
                count += 27;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa27":
                count += 28;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa28":
                count += 29;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa29":
                count += 30;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa30":
                count += 31;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa31":
                count += 32;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa32":
                count += 33;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa33":
                count += 34;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa34":
                count += 35;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa35":
                count += 36;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa36":
                count += 37;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa37":
                count += 38;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa38":
                count += 39;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa39":
                count += 40;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa40":
                count += 41;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa41":
                count += 42;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa42":
                count += 43;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa43":
                count += 44;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa44":
                count += 45;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa45":
                count += 46;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa46":
                count += 47;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa47":
                count += 48;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa48":
                count += 49;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa49":
                count += 50;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa50":
                count += 51;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa51":
                count += 52;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa52":
                count += 53;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa53":
                count += 54;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa54":
                count += 55;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa55":
                count += 56;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa56":
                count += 57;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa57":
                count += 58;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa58":
                count += 59;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa59":
                count += 60;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa60":
                count += 61;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa61":
                count += 62;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa62":
                count += 63;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa63":
                count += 64;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa64":
                count += 65;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa65":
                count += 66;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa66":
                count += 67;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa67":
                count += 68;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa68":
                count += 69;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa69":
                count += 70;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa70":
                count += 71;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa71":
                count += 72;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa72":
                count += 73;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa73":
                count += 74;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa74":
                count += 75;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa75":
                count += 76;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa76":
                count += 77;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa77":
                count += 78;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa78":
                count += 79;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa79":
                count += 80;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa80":
                count += 81;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa81":
                count += 82;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa82":
                count += 83;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa83":
                count += 84;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa84":
                count += 85;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa85":
                count += 86;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa86":
                count += 87;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa87":
                count += 88;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa88":
                count += 89;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa89":
                count += 90;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa90":
                count += 91;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa91":
                count += 92;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa92":
                count += 93;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa93":
                count += 94;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa94":
                count += 95;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa95":
                count += 96;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa96":
                count += 97;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa97":
                count += 98;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa98":
                count += 99;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa99":
                count += 100;
                break;
            default:
                count += 1000;
        }

    }

    public static void stringMutatedDefault() {
        long count = 0;
        switch (MUID_FINAL_STRING) {
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa_dummy":
                count += 1;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa1":
                count += 2;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa2":
                count += 3;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa3":
                count += 4;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa4":
                count += 5;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa5":
                count += 6;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa6":
                count += 7;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa7":
                count += 8;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa8":
                count += 9;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa9":
                count += 10;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa10":
                count += 11;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa11":
                count += 12;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa12":
                count += 13;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa13":
                count += 14;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa14":
                count += 15;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa15":
                count += 16;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa16":
                count += 17;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa17":
                count += 18;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa18":
                count += 19;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa19":
                count += 20;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa20":
                count += 21;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa21":
                count += 22;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa22":
                count += 23;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa23":
                count += 24;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa24":
                count += 25;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa25":
                count += 26;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa26":
                count += 27;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa27":
                count += 28;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa28":
                count += 29;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa29":
                count += 30;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa30":
                count += 31;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa31":
                count += 32;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa32":
                count += 33;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa33":
                count += 34;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa34":
                count += 35;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa35":
                count += 36;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa36":
                count += 37;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa37":
                count += 38;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa38":
                count += 39;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa39":
                count += 40;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa40":
                count += 41;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa41":
                count += 42;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa42":
                count += 43;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa43":
                count += 44;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa44":
                count += 45;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa45":
                count += 46;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa46":
                count += 47;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa47":
                count += 48;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa48":
                count += 49;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa49":
                count += 50;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa50":
                count += 51;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa51":
                count += 52;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa52":
                count += 53;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa53":
                count += 54;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa54":
                count += 55;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa55":
                count += 56;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa56":
                count += 57;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa57":
                count += 58;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa58":
                count += 59;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa59":
                count += 60;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa60":
                count += 61;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa61":
                count += 62;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa62":
                count += 63;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa63":
                count += 64;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa64":
                count += 65;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa65":
                count += 66;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa66":
                count += 67;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa67":
                count += 68;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa68":
                count += 69;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa69":
                count += 70;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa70":
                count += 71;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa71":
                count += 72;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa72":
                count += 73;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa73":
                count += 74;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa74":
                count += 75;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa75":
                count += 76;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa76":
                count += 77;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa77":
                count += 78;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa78":
                count += 79;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa79":
                count += 80;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa80":
                count += 81;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa81":
                count += 82;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa82":
                count += 83;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa83":
                count += 84;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa84":
                count += 85;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa85":
                count += 86;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa86":
                count += 87;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa87":
                count += 88;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa88":
                count += 89;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa89":
                count += 90;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa90":
                count += 91;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa91":
                count += 92;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa92":
                count += 93;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa93":
                count += 94;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa94":
                count += 95;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa95":
                count += 96;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa96":
                count += 97;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa97":
                count += 98;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa98":
                count += 99;
                break;
            case "ae7f9682-6bd8-45e8-a3da-4020ccc153fa99":
                count += 100;
                break;
            default:
                count += 1000;
        }
    }
}
