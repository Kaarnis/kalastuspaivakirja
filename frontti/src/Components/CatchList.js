import React, { useState } from 'react';
import axios from 'axios';
import AddCatchForm from './AddCatchForm';

const CatchList = ({ catches, tripId, onCatchAdded }) => {
  const [showAddCatchForm, setShowAddCatchForm] = useState(false);

  const handleAddCatch = () => {
    setShowAddCatchForm(true);
  };

  const handleCatchAdded = (newCatch) => {
    // Call the API to save the new catch to the backend and associate it with the selected fishing trip
    axios
      .post(`http://localhost:8080/api/fishingtrips/${tripId}/catches`, newCatch)
      .then((response) => {
        console.log('New Catch:', response.data);

        // Notify the parent component (FishingTripList) that a new catch has been added
        onCatchAdded(tripId, response.data);
      })
      .catch((error) => {
        console.error('Error adding catch:', error);
      });
  };

  const handleDeleteCatch = (catchId) => {
    axios
      .delete(`http://localhost:8080/api/fishingtrips/${tripId}/catches/${catchId}`)
      .then(() => {
        // Reload the fishing trip after successful deletion
        window.location.reload();
      })
      .catch((error) => {
        console.error('Error deleting catch:', error);
      });
  };

  return (
    <div className="catch-list">
      <h2>Reissun saalis</h2>
      <ul>
        {catches.map((catchItem) => (
          <li key={catchItem.id}>
            Species: {catchItem.species}, Weight: {catchItem.weight}, Length: {catchItem.length}
            <button onClick={() => handleDeleteCatch(catchItem.id)}>Delete Catch</button>
          </li>
        ))}
      </ul>

      {/* Show AddCatchForm when the button is clicked */}
      {showAddCatchForm && <AddCatchForm onCatchAdded={handleCatchAdded} />}

      {/* Show the button to add a new catch */}
      {!showAddCatchForm && (
        <button type="button" onClick={handleAddCatch}>
          Add Catch
        </button>
      )}
    </div>
  );
};

export default CatchList;
