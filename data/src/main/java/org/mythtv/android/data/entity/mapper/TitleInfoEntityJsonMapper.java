package org.mythtv.android.data.entity.mapper;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.TitleInfoListEntity;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dmfrey on 8/27/15.
 */
public class TitleInfoEntityJsonMapper {

    private static final String TAG = TitleInfoEntityJsonMapper.class.getSimpleName();

    private final Gson gson;

    @Inject

    public TitleInfoEntityJsonMapper() {

        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE )
                .setPrettyPrinting()
                .serializeNulls()
                .create();

    }

    public TitleInfoEntity transformTitleInfoEntity( String titleInfoJsonResponse ) throws JsonSyntaxException {

        Type titleInfoEntityType = new TypeToken<TitleInfoEntity>() {}.getType();

        return this.gson.fromJson( titleInfoJsonResponse, titleInfoEntityType );
    }

    public List<TitleInfoEntity> transformTitleInfoEntityCollection( String titleInfoListJsonResponse ) throws JsonSyntaxException {

        Type titleInfoListEntityType = new TypeToken<TitleInfoListEntity>() {}.getType();
        TitleInfoListEntity titleInfoListEntity = gson.fromJson( titleInfoListJsonResponse, titleInfoListEntityType );

        return Arrays.asList( titleInfoListEntity.getTitleInfos().getTitleInfos() );
    }

}
