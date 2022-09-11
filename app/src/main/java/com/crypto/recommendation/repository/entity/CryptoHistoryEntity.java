package com.crypto.recommendation.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "crypto_history")
@Setter @Getter
@NoArgsConstructor
public class CryptoHistoryEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "timestamp")
	private LocalDateTime timestamp;
	@Column(name = "symbol")
	private String symbol;
	@Column(name = "price")
	private BigDecimal price;

	public CryptoHistoryEntity(LocalDateTime timestamp, String symbol, BigDecimal price) {
		this.timestamp = timestamp;
		this.symbol = symbol;
		this.price = price;
	}
}