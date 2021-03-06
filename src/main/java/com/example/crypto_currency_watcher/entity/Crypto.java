package com.example.crypto_currency_watcher.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "crypto")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Crypto {
    @Id
    private Long id;
    @Column
    private String symbol;
    @Column
    private Double price_usd;
    @OneToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private User users;
}
