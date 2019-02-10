import React from 'react';
import { mount } from 'enzyme';
import App from '../App';

describe('this', () => {
  it('smoke test', () => {
    mount(<App />);
  });
});
