package ca.cmpt213.as5.model;

/*
    This class is responsible for storing the
    catalog number of the course. It provides
    the user the information about the catalog
    number. A user can set and receive catalog-
    numbers using this class.
 */
public class CatalogNumber {
    private String catalogNum;

    public CatalogNumber(){}

    public CatalogNumber(String catalogNum){
        this.catalogNum = catalogNum;
    }

    public void setCatalogNum(String catalogNum){
        this.catalogNum = catalogNum;
    }

    public String getCatalogNum(){
        return catalogNum;
    }

    public boolean equals(CatalogNumber other){
        return catalogNum.equalsIgnoreCase(other.catalogNum);
    }
}
