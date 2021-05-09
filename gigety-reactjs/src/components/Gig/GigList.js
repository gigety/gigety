import React from 'react';
import PropTypes from 'prop-types';
import { List } from 'semantic-ui-react';
import GigLabel from './GigLabel';

const GigList = ({ gigList }) => {
	return (
		<List divided verticalAlign="middle" className="scroll-list medium">
			{gigList
				? gigList.map((gig) => {
						//TODO: this is hacky, make a revisit
						const linkTo =
							window.location.pathname === '/user/Account'
								? `/user/gig/detail/${gig.id}`
								: `/guest/gig/detail/${gig.id}`;
						return <GigLabel key={gig.id} gig={gig} linkTo={linkTo} />;
				  })
				: ''}
		</List>
	);
};

GigList.propTypes = { gigList: PropTypes.array };

export default GigList;
