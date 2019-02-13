import React from 'react';
import Enzyme, { mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ModalDialog from '../../modalDialog/ModalDialog';

Enzyme.configure({ adapter: new Adapter() });

describe("Modal dialog", ()=>{
    const props = {
    }
    it('renders without error', () => {
    expect(mount(<ModalDialog {...props} />).length).toEqual(1);
    });

    // describe('close button', () => {
    // it('diplays a button on the dialog', () => {
    //   const modal = mount(<SimpleModal {...props} />);
    //   const received = modal.find('button');
    //   expect(received.length).toEqual(1);
    // });
    // })
})