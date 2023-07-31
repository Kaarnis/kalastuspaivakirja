import React, { useState } from 'react';
import FishingTripList from './Components/FishingTripList';
import AddFishingTripForm from './Components/AddFishingTripForm';
import './css/styles.css';

// ... other imports ...

const App = () => {
  const [refresh, setRefresh] = useState(false);

  const handleTripAdded = () => {
    setRefresh(!refresh);
  };

  return (
    <div className="container">
      <main>
        <div className="add-fishing-trip">
          <h2>Add Fishing Trip</h2>
          <AddFishingTripForm onTripAdded={handleTripAdded} />
        </div>
        <div className="fishing-trips">
          <h2>Fishing Trips</h2>
          <FishingTripList refresh={refresh} onTripAdded={handleTripAdded} />
        </div>
      </main>
    </div>
  );
};

export default App;
