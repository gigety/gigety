import React, { useState, useCallback } from 'react';
import PropTypes from 'prop-types';
import { Card, Icon, CardHeader, CardContent } from 'semantic-ui-react';
import '../Styles/ImageStyles.css';
import { useSelector } from 'react-redux';
function FileDropzone({ disabled, onFilesAdded }) {
	const [highlight, setHighlight] = useState(true);
	const fileInputRef = React.createRef();
	const { giguser } = useSelector((state) => state.giguser);

	const onDragOver = useCallback(
		(e) => {
			e.preventDefault();
			if (disabled) {
				return;
			}
			setHighlight(true);
		},
		[setHighlight, disabled]
	);
	const openFileDialog = useCallback(
		(e) => {
			if (disabled) {
				return;
			}
			fileInputRef.current.click();
		},
		[fileInputRef, disabled]
	);
	const onDrop = (e) => {
		e.preventDefault();
		if (disabled) {
			return;
		}
		const files = e.dataTransfer.files;
		console.log(files);
		if (onFilesAdded) {
			onFilesAdded(files);
		}
		updateImageRef(files);
		setHighlight(false);
	};

	const addFiles = (e) => {
		if (disabled) {
			return;
		}
		const files = e.target.files;

		console.log(files);
		if (onFilesAdded) {
			onFilesAdded(files);
		}
		updateImageRef(files);
	};

	const updateImageRef = (files) => {
		const reader = new FileReader();
		reader.onload = function (event) {
			document.querySelector('#imageRef').src = reader.result;
		};
		reader.readAsDataURL(files[0]);
	};
	const onDragLeave = (e) => {
		e.preventDefault();
		if (disabled) {
			return;
		}
		setHighlight(false);
	};
	return (
		<Card centered>
			<CardContent>
				<CardHeader>
					<Icon name="upload" />
					<span>Upload Image</span>
				</CardHeader>
			</CardContent>
			<CardContent>
				<div
					className={`Dropzone ${highlight ? 'Highlight' : ''}`}
					onDragOver={onDragOver}
					onDragLeave={onDragLeave}
					onDrop={onDrop}
					onClick={openFileDialog}
					style={{ cursor: disabled ? 'default' : 'pointer' }}
				>
					<img id="imageRef" alt="Profile Avatar" src={giguser ? giguser.imageUrl : '/frog.png'} />
					<input ref={fileInputRef} className="FileInput" type="file" multiple onChange={addFiles} />
				</div>
			</CardContent>
		</Card>
	);
}

FileDropzone.propTypes = {
	disabled: PropTypes.bool,
	onFilesAdded: PropTypes.func,
};

export default FileDropzone;
