package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by dmfrey on 1/18/16.
 */
@Data
public class EncodersEntity {

    @SerializedName( "Encoders" )
    private EncoderEntity[] encoders;

}
