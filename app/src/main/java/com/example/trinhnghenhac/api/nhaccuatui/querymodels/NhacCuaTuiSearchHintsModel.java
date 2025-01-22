package com.example.trinhnghenhac.api.nhaccuatui.querymodels;

import com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiArtistModel;
import com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiPlaylistModel;
import com.example.trinhnghenhac.api.nhaccuatui.models.NhacCuaTuiSongModel;

import java.io.Serializable;
import java.util.List;

public class NhacCuaTuiSearchHintsModel extends NhacCuaTuiQueryModel implements Serializable {
    public List<NhacCuaTuiArtistModel> artist;
    public List<NhacCuaTuiPlaylistModel> playlist;
    public List<NhacCuaTuiSongModel> song;
}
