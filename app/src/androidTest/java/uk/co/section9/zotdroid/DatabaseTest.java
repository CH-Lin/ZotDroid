package uk.co.section9.zotdroid;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import uk.co.section9.zotdroid.data.ZotDroidDB;
import uk.co.section9.zotdroid.data.zotero.Author;
import uk.co.section9.zotdroid.data.zotero.Record;
import uk.co.section9.zotdroid.data.zotero.Summary;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("uk.co.section9.zotdroid", appContext.getPackageName());
    }

    @Test
    public void testRecordDB() throws Exception {
        // Context of the app under test.
        // Apparently this test runs on a device - instrumentation tests essentially
        Context appContext = InstrumentationRegistry.getTargetContext();
        ZotDroidDB db = new ZotDroidDB(appContext);

        Record record = new Record();
        record.set_version("1234");
        record.add_author(new Author("author","1234"));
        record.set_content_type("pdf");
        record.set_date_added("testdate");
        record.set_parent("abcd");
        record.set_title("title");
        record.set_item_type("type");
        db.writeRecord(record);
        int numrow = db.getNumRecords();
        Record r2 = db.getRecord(numrow-1);

        assertEquals(r2.get_version(),"1234");
        assertEquals(r2.get_authors().elementAt(0),"author");
        assertEquals(r2.get_content_type(),"pdf");
        assertEquals(r2.get_parent(),"abcd");
        assertEquals(r2.get_title(),"title");
        assertEquals(r2.get_item_type(),"type");

    }

    @Test
    public void testSummaryDB() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        ZotDroidDB db = new ZotDroidDB(appContext);

        Summary s = new Summary();

        db.writeSummary(s);

        Summary summary = db.getSummary();
        assertEquals(summary.get_last_version(),"0000");

        s.set_last_version("1234");
        db.writeSummary(s);

        summary = db.getSummary();
        assertEquals(summary.get_last_version(),"1234");

    }
}
