import React from 'react';
import { Icon } from 'semantic-ui-react';
const locate = ({ panTo, size }) => {
	return (
		<div className="locate-ctrl">
			<Icon
				name="compass"
				size={size}
				color="orange"
				onClick={() => {
					navigator.geolocation.getCurrentPosition(({ coords }) => {
						panTo({
							lat: coords.latitude,
							lng: coords.longitude,
						});
					});
				}}
			/>
		</div>
	);
};
locate.defaultProps = {
	size: 'big',
};
export default locate;
