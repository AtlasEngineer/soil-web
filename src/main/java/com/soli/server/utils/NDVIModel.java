package com.soli.server.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class NDVIModel {

    private String name;
    private String path;
    private float[][] data;
    private Double noData;
    private String geom;
    private Integer tk_id;
    private Date data_time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NDVIModel ndviModel = (NDVIModel) o;
        return Objects.equals(name, ndviModel.name) &&
                Objects.equals(path, ndviModel.path) &&
                Arrays.equals(data, ndviModel.data) &&
                Objects.equals(noData, ndviModel.noData) &&
                Objects.equals(geom, ndviModel.geom) &&
                Objects.equals(tk_id, ndviModel.tk_id) &&
                Objects.equals(data_time, ndviModel.data_time);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, path, noData, geom, tk_id, data_time);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTk_id() {
        return tk_id;
    }

    public void setTk_id(Integer tk_id) {
        this.tk_id = tk_id;
    }

    public Date getData_time() {
        return data_time;
    }

    public void setData_time(Date data_time) {
        this.data_time = data_time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public float[][] getData() {
        return data;
    }

    public void setData(float[][] data) {
        this.data = data;
    }

    public Double getNoData() {
        return noData;
    }

    public void setNoData(Double noData) {
        this.noData = noData;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }
}
