import React from 'react';
import spinner from './spinner.gif';

export default ({width}) => {
  return (
    <div>
      <img
        src={spinner}
        style={{ margin: 'auto', display: 'block' }}
        width = {width || 60}
        alt="Loading..."
      />
    </div>
  );
};

