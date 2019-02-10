import React from 'react';
import { mount } from 'enzyme';
import ProjectTile from '../ProjectTile';

describe('Project tile', () => {
  const props = {
    name: 'default',
    date: new Date(),
    isMenuOpen: false,
    toggleMenu: () => {},
    handleUpdate: () => {},
    handleDelete: () => {},
  };

  it('renders without error', () => {
    expect(mount(<ProjectTile {...props} />).length).toEqual(1);
  });

  it('renders project title as expected', () => {
    const tile = mount(<ProjectTile {...props} name="test" />);
    const received = tile.find('h2').text();
    expect(received).toEqual('test');
  });

  it('displays a created date', () => {
    const date = new Date(1, 2, 1999);
    const tile = mount(<ProjectTile {...props} date={date} />);
    const received = tile.find('[data-test="tile__date"]').text();
    expect(received).toEqual(date.toDateString());
  });

  describe('menu button', () => {
    it('diplays a button on the tile', () => {
      const tile = mount(<ProjectTile {...props} />);
      const received = tile.find('button');
      expect(received.length).toEqual(1);
    });

    it('calls a given toggle function on click', () => {
      const toggle = jest.fn(); // jest mock function
      const tile = mount(<ProjectTile {...props} toggleMenu={toggle} />);
      tile.find('button').simulate('click');
      expect(toggle).toHaveBeenCalled();
    });

    it('renders a menu when given prop is true', () => {
      const tile = mount(<ProjectTile {...props} isMenuOpen />);
      expect(tile.find('ol').length).toEqual(1);
    });

    it('does not render a menu when given prop is false', () => {
      const tile = mount(<ProjectTile {...props} isMenuOpen={false} />);
      expect(tile.find('ol').length).toEqual(0);
    });

    it('renders the list of items', () => {
      const tile = mount(<ProjectTile {...props} isMenuOpen />);
      expect(tile.find('li').length).toEqual(2);
    });

    it('has an update project button', () => {
      const update = jest.fn();
      const tile = mount(
        <ProjectTile {...props} isMenuOpen handleUpdate={update} />
      );
      tile.find("[data-test='tile__update']").simulate('click');
      expect(update).toHaveBeenCalled();
    });

    it('has an delete project button', () => {
      const deleteProject = jest.fn();
      const tile = mount(
        <ProjectTile {...props} isMenuOpen handleDelete={deleteProject} />
      );
      tile.find("[data-test='tile__delete']").simulate('click');
      expect(deleteProject).toHaveBeenCalled();
    });
  });
});
