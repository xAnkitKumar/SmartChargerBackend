package entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "charging_stations")
public class ChargingStations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;
    
    // Replace org.locationtech.jts.geom.Point with a placeholder class if the dependency is missing
    @Column(columnDefinition = "POINT SRID 4326", nullable = false)
    private Object location;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('AC_LEVEL_2','DC_FAST','TESLA_SUPERCHARGER')")
    private ChargerType chargerType;
    
    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Short totalPorts;
    
    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Short availablePorts;
    
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal chargingRateKw;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private String supportedPlugs;
    
    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isActive;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('Bangalore','Mumbai','Hyderabad','Delhi')")
    private City city;
    
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp lastUpdated;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Object getLocation() {
        return location;
    }
    public void setLocation(Object location) {
        this.location = location;
    }
    public ChargerType getChargerType() {
        return chargerType;
    }
    public void setChargerType(ChargerType chargerType) {
        this.chargerType = chargerType;
    }
    public Short getTotalPorts() {
        return totalPorts;
    }
    public void setTotalPorts(Short totalPorts) {
        this.totalPorts = totalPorts;
    }
    public Short getAvailablePorts() {
        return availablePorts;
    }
    public void setAvailablePorts(Short availablePorts) {
        this.availablePorts = availablePorts;
    }
    public BigDecimal getChargingRateKw() {
        return chargingRateKw;
    }
    public void setChargingRateKw(BigDecimal chargingRateKw) {
        this.chargingRateKw = chargingRateKw;
    }
    public String getSupportedPlugs() {
        return supportedPlugs;
    }
    public void setSupportedPlugs(String supportedPlugs) {
        this.supportedPlugs = supportedPlugs;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public enum ChargerType {
        AC_LEVEL_2, DC_FAST, TESLA_SUPERCHARGER
    }
    
    public enum City {
        Bangalore, Mumbai, Hyderabad, Delhi
    }
}