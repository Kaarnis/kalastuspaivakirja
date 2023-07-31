import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CatchList from './CatchList';
import '../css/styles.css';

// ... other imports ...

const FishingTripList = ({ refresh, onTripAdded }) => {
  const [fishingTrips, setFishingTrips] = useState([]);

  useEffect(() => {
    fetchFishingTrips();
  }, [refresh]);

  const fetchFishingTrips = () => {
    axios
      .get('http://localhost:8080/api/fishingtrips')
      .then((response) => setFishingTrips(response.data))
      .catch((error) => console.error('Error fetching fishing trips:', error));
  };

  // Function to handle adding a catch to a specific trip
  const handleCatchAdded = (tripId, newCatch) => {
    // Find the trip to which we want to add the catch
    const updatedTrips = fishingTrips.map((trip) =>
      trip.id === tripId ? { ...trip, catches: [...trip.catches, newCatch] } : trip
    );

    // Update the state with the new list of trips
    setFishingTrips(updatedTrips);
  };

  return (
    <div className="fishing-trips">
      <ul>
        {fishingTrips.map((trip) => (
          <li key={trip.id}>
            <h3>{trip.title} - {trip.date}</h3>
            <CatchList tripId={trip.id} catches={trip.catches} onCatchAdded={handleCatchAdded} />
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FishingTripList;

