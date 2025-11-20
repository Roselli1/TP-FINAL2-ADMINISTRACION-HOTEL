package Gestores;

import Interfaces.iToJSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GestoraGenerica <T extends iToJSON> implements iToJSON
{
    protected final String nombreJson;
    protected final List<T> elementos;

    public GestoraGenerica(String nombreJSON)
    {
        this.nombreJson = nombreJSON;
        this.elementos = new ArrayList<>();
    }

    public void agregarElemento(T elemento)
    {
        if (elemento != null) {
            this.elementos.add(elemento);
        }
    }

    public List<T> getElementos() {
        return elementos;
    }


    @Override
    public JSONObject toJSON() {
        JSONObject jsonGestora = new JSONObject();
        JSONArray jsonElementos = new JSONArray();

        for (T elemento : this.elementos) {
            jsonElementos.put(elemento.toJSON());
        }

        jsonGestora.put(this.nombreJson, jsonElementos);

        return jsonGestora;
    }


    public void guardar(String nombreArchivo) {
        JSONArray arrayFinal = new JSONArray();
        arrayFinal.put(this.toJSON());

        JsonUtiles.grabarUnJson(arrayFinal, nombreArchivo + ".json");
    }


}
