package com.cg.model;

import com.cg.model.dto.LocationRegionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "location_region")
@Accessors(chain = true)
public class LocationRegion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "province_id")
    private String provinceId;

    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "district_id")
    private String districtId;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "ward_id")
    private String wardId;

    @Column(name = "ward_name")
    private String wardName;

    private String address;

    @OneToOne(mappedBy = "locationRegion")
    private Customer customer;


    public LocationRegionDTO toLocationRegionDTO() {
        return new LocationRegionDTO()
                .setId(id)
                .setProvinceId(provinceId)
                .setProvinceName(provinceName)
                .setDistrictId(districtId)
                .setDistrictName(districtName)
                .setWardId(wardId)
                .setWardName(wardName)
                .setAddress(address);

//        LocationRegionDTO locationRegionDTO = new LocationRegionDTO();
//        locationRegionDTO.setId(id);
//        locationRegionDTO.setProvinceId(provinceId);
//        locationRegionDTO.setProvinceName(provinceName);
//        locationRegionDTO.setDistrictId(districtId);
//        locationRegionDTO.setDistrictName(districtName);
//        locationRegionDTO.setWardId(wardId);
//        locationRegionDTO.setWardName(wardName);
//        locationRegionDTO.setAddress(address);
//
//        return locationRegionDTO;
    }

}
