package husky.note.huskynote.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import husky.note.huskynote.database.AppDataBase;

/**
 * Created by Administrator on 2017/10/18.
 */

@Table(name = "RelationshipOfNoteTag", database = AppDataBase.class)
public class RelationshipOfNoteTag
{
    @Column(name = "id")
    @PrimaryKey(autoincrement = true)
    long id;

    @Column(name = "noteLocalId")
    long noteLocalId;

    @Column(name = "tagLocalId")
    long tagLocalId;

    @Column(name = "userId")
    String userId;

    RelationshipOfNoteTag() {
    }

    public RelationshipOfNoteTag(long noteLocalId, long tagLocalId, String userId) {
        this.noteLocalId = noteLocalId;
        this.tagLocalId = tagLocalId;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public long getNoteLocalId() {
        return noteLocalId;
    }

    public long getTagLocalId() {
        return tagLocalId;
    }
}
