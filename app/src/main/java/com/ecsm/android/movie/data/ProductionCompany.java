package com.ecsm.android.movie.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "productionCompany")
public class ProductionCompany extends Model {

    @Column(name = "name")
    @SerializedName("name")
    @Expose
    private String name;

    @Column(name = "companyId", unique = true, notNull = true, onUniqueConflict = Column.ConflictAction.IGNORE)
    @SerializedName("id")
    @Expose
    private Integer companyId;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The id
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * @param id The id
     */
    public void setCompanyId(Integer id) {
        companyId = id;
    }

}
