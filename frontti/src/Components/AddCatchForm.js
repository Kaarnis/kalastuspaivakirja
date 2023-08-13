import React, { useState } from 'react';

const AddCatchForm = ({ onCatchAdded }) => {
  const [species, setSpecies] = useState('');
  const [weightKg, setWeightKg] = useState('');
  const [lengthCm, setLengthCm] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();

    // Convert weight to kilograms and length to centimeters
    const weight = parseFloat(weightKg);
    const length = parseFloat(lengthCm);

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
    setWeightKg('');
    setLengthCm('');
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Species"
        value={species}
        onChange={(e) => setSpecies(e.target.value)}
        required
      />

      <input
        type="number"
        value={weightKg}
        placeholder="Weight (KG)"
        onChange={(e) => setWeightKg(e.target.value)}
        step="0.01" // Use step attribute to allow decimals
        required
      />

      <input
        type="number"
        placeholder="Length (CM)"
        value={lengthCm}
        onChange={(e) => setLengthCm(e.target.value)}
        required
      />

      <button type="submit">Add Catch</button>
    </form>
  );
};

export default AddCatchForm;