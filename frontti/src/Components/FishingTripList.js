import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CatchList from './CatchList';
import '../css/styles.css';

const FishingTripList = ({ refresh }) => {
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

  const handleDeleteFishingTrip = (tripId) => {
    axios
      .delete(`http://localhost:8080/api/fishingtrips/${tripId}`)
      .then(() => {
        // Reload the fishing trips after successful deletion
        window.location.reload();
      })
      .catch((error) => {
        console.error('Error deleting fishing trip:', error);
      });
  };

  return (
    <div className="fishing-trips">
      <h2>Fishing Trips</h2>
      {fishingTrips.map((trip) => (
        <div className="trip-card" key={trip.id}>
          <h3>{trip.title} - {trip.date}</h3>
          <div className="trip-details">
            <p><strong>Location:</strong> {trip.location}</p>
            <p><strong>Weather:</strong> {trip.weather}</p>
            <p><strong>Notes:</strong> {trip.notes}</p>
            <CatchList catches={trip.catches} tripId={trip.id} />
            <button onClick={() => handleDeleteFishingTrip(trip.id)}>Delete Trip</button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default FishingTripList;

