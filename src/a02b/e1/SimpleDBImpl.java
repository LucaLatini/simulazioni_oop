package a02b.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleDBImpl implements SimpleDB {
    private Map<String, Map<Integer, Map<String, Object>>> DB = new HashMap<>();

    @Override
    public void createTable(String name) {
        this.DB.put(name, new HashMap<>());
    }

    @Override
    public void addTuple(String tableName, int id, String description, Object value) {
        var table = this.DB.get(tableName);
        if (!table.containsKey(id)) {
            table.put(id, new HashMap<>());
        }
        var row = table.get(id);
        row.put(description, value);
    }

    @Override
    public Set<Integer> ids(String name) {
        return this.DB.get(name).keySet();

    }

    @Override
    public Map<String, Object> values(String name, int id) {
        return this.DB.get(name).get(id);
    }

    @Override
    public void createViewOfSingleDescription(String viewName, String originName, String description) {
        this.createTable(viewName);
        var originTable = this.DB.get(originName);
        originTable.forEach((k, v) -> {
            if (v.containsKey(description)) {
                this.addTuple(viewName, k, description, v.get(description));
            }
        });

    }

    @Override
    public void createViewOfSingleId(String viewName, String originName, int id) {
        this.createTable(viewName);
        var originTable = this.DB.get(originName);
        for (int key : originTable.keySet()) {
            if (key == id) {
                Map<String, Object> valuesMap = originTable.get(key);
                valuesMap.forEach((description, value) -> {
                    this.addTuple(viewName, key, description, value);
                });
            }
        }
    }

}
