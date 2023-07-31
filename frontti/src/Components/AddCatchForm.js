import React, { useState } from 'react';

const AddCatchForm = ({ onCatchAdded }) => {
  const [species, setSpecies] = useState('');
  const [weight, setWeight] = useState('');
  const [length, setLength] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();

    // Create the new catch object
    const newCatch = {
      species,
      weight,
      length,
    };

    // Pass the new catch to the parent component
    onCatchAdded(newCatch);

    // Clear the form fields after successful addition
    setSpecies('');
    setWeight('');
    setLength('');
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>Species:</label>
      <input
        type="text"
        value={species}
        onChange={(e) => setSpecies(e.target.value)}
        required
      />

      <label>Weight:</label>
      <input
        type="number"
        value={weight}
        onChange={(e) => setWeight(e.target.value)}
        required
      />

      <label>Length:</label>
      <input
        type="number"
        value={length}
        onChange={(e) => setLength(e.target.value)}
        required
      />

      <button type="submit">Add Catch</button>
    </form>
  );
};

export default AddCatchForm;
