import React, { useState } from 'react';
import axios from 'axios';
import '../css/FishingTripForm.css'

const AddFishingTripForm = ({ onTripAdded }) => {
  const [title, setTitle] = useState('');
  const [date, setDate] = useState('');
  const [location, setLocation] = useState('');
  const [weather, setWeather] = useState('');
  const [notes, setNotes] = useState('');
  const [catches, setCatches] = useState([{ species: '', weight: '', length: '' }]);

  const handleCatchChange = (index, field, value) => {
    const updatedCatches = [...catches];
    updatedCatches[index][field] = value;
    setCatches(updatedCatches);
  };

  const handleAddCatch = () => {
    setCatches([...catches, { species: '', weight: '', length: '' }]);
  };

  const handleRemoveCatch = (index) => {
    const updatedCatches = [...catches];
    updatedCatches.splice(index, 1);
    setCatches(updatedCatches);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const newFishingTrip = {
      title,
      date,
      location,
      weather,
      notes,
      catches,
    };

    axios
      .post('http://localhost:8080/api/fishingtrips', newFishingTrip)
      .then((response) => {
        console.log('Fishing trip added successfully:', response.data);

        onTripAdded(); // Notify the parent component that a new trip has been added

        setTitle('');
        setDate('');
        setLocation('');
        setWeather('');
        setNotes('');
        setCatches([{ species: '', weight: '', length: '' }]);
      })
      .catch((error) => {
        console.error('Error adding fishing trip:', error);
      });
  };
  return (
    <form className="fishing-trip-form" onSubmit={handleSubmit}>
      <div className="Kalareissuformi">
        <div className="form-group">
          <label>Title:</label>
          <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} required />
        </div>

        <div className="form-group">
          <label>Date:</label>
          <input type="date" value={date} onChange={(e) => setDate(e.target.value)} required />
        </div>

        <div className="form-group">
          <label>Location:</label>
          <input type="text" value={location} onChange={(e) => setLocation(e.target.value)} required />
        </div>

        <div className="form-group">
          <label>Weather:</label>
          <input type="text" value={weather} onChange={(e) => setWeather(e.target.value)} />
        </div>

        <div className="form-group">
          <label>Notes:</label>
          <textarea value={notes} onChange={(e) => setNotes(e.target.value)} />
        </div>
      </div>
      <div className="catchform-group">
        <label>Catches:</label>
        {catches.map((catchItem, index) => (
          <div key={index} className="catch-form">
            <input
              type="text"
              value={catchItem.species}
              onChange={(e) => handleCatchChange(index, 'species', e.target.value)}
              placeholder="Species"
              required
            />
            <input
              type="number"
              value={catchItem.weight}
              onChange={(e) => handleCatchChange(index, 'weight', e.target.value)}
              placeholder="Weight (KG)" // Update placeholder
              required
            />
            <input
              type="number"
              value={catchItem.length}
              onChange={(e) => handleCatchChange(index, 'length', e.target.value)}
              placeholder="Length (CM)" // Update placeholder
              required
            />
            <button className ="removebutton" type="button" onClick={() => handleRemoveCatch(index)}>
              Remove
            </button>
          </div>
        ))}
      </div>

      <div className="fishingtripformbuttons">
        <button type="button" onClick={handleAddCatch}>
          Add Catch
        </button>
        <button type="submit">Add Fishing Trip</button>
      </div>
    </form>
  );

};

export default AddFishingTripForm;