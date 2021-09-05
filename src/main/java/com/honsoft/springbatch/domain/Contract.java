package com.honsoft.springbatch.domain;

import java.util.Date;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Contract")
public class Contract {

	private long contractId;
	private String contractName;
	private String carrier;
	private Date fileProcessingTime;

	@XmlAttribute(name = "ContractId")
	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	@XmlElement(name = "ContractName")
	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@XmlElement(name = "Carrier")
	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	@XmlElement(name = "ProcessingTime")
	public Date getFileProcessingTime() {
		return fileProcessingTime;
	}

	public void setFileProcessingTime(Date fileProcessingTime) {
		this.fileProcessingTime = fileProcessingTime;
	}

}