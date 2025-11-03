package ec.edu.monster.controller;

import ec.edu.monster.model.ConversionModel;

public class ConversionController {

    public double convert(String category, String from, String to, double value) throws Exception {
        return ConversionModel.convert(category, from, to, value);
    }
}