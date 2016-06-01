/*
 * Artcodes recognises a different marker scheme that allows the
 * creation of aesthetically pleasing, even beautiful, codes.
 * Copyright (C) 2013-2016  The University of Nottingham
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.horizon.artcodes.detect.marker;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import uk.ac.horizon.artcodes.model.Action;

public class MarkerCodeDetectionHandler implements MarkerDetectionHandler
{
    public interface CodeDetectionHandler
    {
        /**
         * Called when a marker is detected.
         * @param code The code representation of the marker.
         */
        void onMarkerCodeDetected(String code);
    }

    protected static final int REQUIRED = 20;
    protected static final int MAX = REQUIRED * 4;

    protected final Multiset<String> markerCounts = HashMultiset.create();
    private CodeDetectionHandler markerCodeHandler;

    public MarkerCodeDetectionHandler(CodeDetectionHandler markerCodeHandler)
    {
        this.markerCodeHandler = markerCodeHandler;
    }

    @Override
    public void onMarkersDetected(Collection<Marker> markers, ArrayList<MatOfPoint> contours, Mat hierarchy)
    {
        actOnMarkers(countMarkers(markers));
    }

    protected void actOnMarkers(Multiset<String> markers)
    {
        int best = 0;
        String selected = null;
        for (String code : markers.elementSet())
        {
            int count = markers.count(code);
            if (count > best)
            {
                selected = code;
                best = count;
            }
        }

        if (selected != null || best >= REQUIRED)
        {
            this.markerCodeHandler.onMarkerCodeDetected(selected);
        }
    }

    protected Multiset<String> countMarkers(Collection<Marker> markers)
    {

        final Collection<String> removals = new HashSet<>(markerCounts.elementSet());

        for (Marker marker : markers)
        {
            final String markerCode = marker.toString();
            final int count = markerCounts.count(markerCode);
            if (count > MAX)
            {
                markerCounts.setCount(markerCode, MAX);
            }

            //increase occurrence if this marker is already in the list.
            markerCounts.add(markerCode);
            removals.remove(markerCode);
        }

        markerCounts.removeAll(removals);

        return markerCounts;
    }
}