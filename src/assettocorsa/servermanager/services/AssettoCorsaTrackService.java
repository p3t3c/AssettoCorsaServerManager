package assettocorsa.servermanager.services;

import assettocorsa.servermanager.services.data.ACTrackData;

import java.util.List;

/**
 * Service to get track data from the game dirs.
 * Created by pete on 7/04/2015.
 */
public interface AssettoCorsaTrackService {
    /**
     * Give the parent dir of the game. Scan and extract information from the directories that is needed to perform configurtion on the UI.
     *
     * @param assettoCorsaParentDir the dir name where assetto corsa is.
     * @return List of ACTrackData empty list the data is not found in dir provided.
     */
    List<ACTrackData> getAllTrackData(String assettoCorsaParentDir);
}
