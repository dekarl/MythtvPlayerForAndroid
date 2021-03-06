package org.mythtv.android.data.net;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 8/27/15.
 */
public interface DvrApi {

    String TITLE_INFO_LIST_URL = "/Dvr/GetTitleInfoList";

    String RECORDED_LIST_BASE_URL = "/Dvr/GetRecordedList";
    String RECORDED_BASE_URL = "/Dvr/GetRecorded?ChanId=%s&StartTime=%s";
    String UPCOMING_LIST_BASE_URL = "/Dvr/GetUpcomingList";
    String ENCODER_LIST_BASE_URL = "/Dvr/GetEncoderList";

    String DESCENDING_QS = "Descending=%s";
    String START_INDEX_QS = "StartIndex=%s";
    String COUNT_QS = "Count=%s";
    String TITLE_REG_EX_QS = "TitleRegEx=%s";
    String REC_GROUP_QS = "RecGroup=%s";
    String STORAGE_GROUP_QS = "StorageGroup=%s";
    String SHOW_ALL_QS = "ShowAll=%s";
    String RECORD_ID_QS = "RecordId=%s";
    String REC_STATUS_QS = "RecStatus=%s";

    Observable<List<TitleInfoEntity>> titleInfoEntityList();

    Observable<List<ProgramEntity>> recordedProgramEntityList( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup );

    Observable<ProgramEntity> recordedProgramById( final int chanId, final DateTime startTime );

    Observable<List<ProgramEntity>> upcomingProgramEntityList( final int startIndex, final int count, final boolean showAll, final int recordId, final int recStatus );

    Observable<List<EncoderEntity>> encoderEntityList();

}
