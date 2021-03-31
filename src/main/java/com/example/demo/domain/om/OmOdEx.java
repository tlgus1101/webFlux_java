package com.example.demo.domain.om;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface OmOdEx{
	String getOdNo();
	String getOdrNm();
	String getOrglOdNo();
	String getMbNo();
}
