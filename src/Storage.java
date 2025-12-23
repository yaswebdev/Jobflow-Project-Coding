// java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final String FILE = "data.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static List<Users> loadUsers() {
        File f = new File(FILE);
        if (!f.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(f)) {
            Type listType = new TypeToken<List<Users>>() {}.getType();
            List<Users> users = gson.fromJson(reader, listType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveUsers(List<Users> users) {
        try (Writer writer = new FileWriter(FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
