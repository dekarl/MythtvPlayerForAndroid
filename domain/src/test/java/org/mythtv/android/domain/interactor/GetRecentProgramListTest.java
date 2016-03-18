package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.DvrRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 3/18/16.
 */
public class GetRecentProgramListTest {

    private GetRecentProgramList getRecentProgramList;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private DvrRepository mockDvrRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        getRecentProgramList = new GetRecentProgramList( mockDvrRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testGetRecentProgramListUseCaseObservableHappyCase() {

        getRecentProgramList.buildUseCaseObservable();

        verify( mockDvrRepository ).recent();
        verifyNoMoreInteractions( mockDvrRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

}
