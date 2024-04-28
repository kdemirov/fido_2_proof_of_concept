package com.iwmnetwork.aqtos.identify.demo.repository.webauthn.authenticator_model;


import lombok.Data;

@Data
public class PublicKey {

    int keyType;
    int alg;
    int curveType;
    byte[] xCoordinate;
    byte[] yCoordinate;

    public PublicKey(int keyType, int alg, int curveType, byte[] xCoordinate, byte[] yCoordinate) {
        this.keyType = keyType;
        this.alg = alg;
        this.curveType = curveType;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public PublicKey() {
    }
}
