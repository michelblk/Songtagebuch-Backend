package de.michelblank.songtagebuch.rest.transferobject;

import de.michelblank.songtagebuch.database.entity.DiaryEntry;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class DiaryEntryTO {
    private String id;
    private Date referenceDate;
    private Date modificationDate;
    private List<SongTO> songs;
    private String entry;

    public static DiaryEntryTO build(final DiaryEntry diaryEntry) {
        return DiaryEntryTO.builder()
                .id(diaryEntry.getId().toString())
                .referenceDate(diaryEntry.getReferenceDate())
                .modificationDate(diaryEntry.getModificationDate())
                .songs(diaryEntry.getSongs().stream().map(SongTO::build).toList())
                .entry(diaryEntry.getEntry())
                .build();
    }
}
