import React, { useState, useCallback } from 'react';
import PropTypes from 'prop-types';
import { Card, Button, ButtonContent, Icon, CardHeader, CardContent } from 'semantic-ui-react';
import '../Styles/ImageStyles.css';
import { useSelector } from 'react-redux';
function FileDropzone(props) {
	const [highlight, setHighlight] = useState(true);
	const fileInputRef = React.createRef();
	const { giguser } = useSelector((state) => state.giguser);

	const onDragOver = useCallback(
		(e) => {
			e.preventDefault();
			if (props.disabled) {
				return;
			}
			setHighlight(true);
		},
		[highlight]
	);
	const openFileDialog = useCallback(
		(e) => {
			if (props.disabled) {
				return;
			}
			fileInputRef.current.click();
		},
		[fileInputRef]
	);
	const onDrop = useCallback(
		(e) => {
			e.preventDefault();
			if (props.disabled) {
				return;
			}
			const files = e.dataTransfer.files;
			console.log('files: ', files);
			if (props.onFilesAdded) {
				props.onFilesAdded(files[0]);
			}
			const reader = new FileReader();
			reader.onload = function (event) {
				console.log(fileInputRef);
				document.querySelector('#imageRef').src = reader.result;
			};
			reader.readAsDataURL(files[0]);
			setHighlight(false);
		},
		[highlight]
	);

	const fileListToArray = (list) => {
		const arr = [];
		for (let i = 0; i < list.length; i++) {
			arr.push(list.item(i));
		}
		return arr;
	};
	const onFilesAdded = (e) => {
		if (props.disabled) {
			return;
		}
		const files = e.target.files;
		if (props.onFilesAdded) {
			console.log(files[0]);
			props.onFilesAdded(files);
		}
		const reader = new FileReader();
		reader.onload = function (event) {
			document.querySelector('#imageRef').src = reader.result;
		};
		reader.readAsDataURL(files[0]);
	};
	const onDragLeave = (e) => {
		e.preventDefault();
		if (props.disabled) {
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
					style={{ cursor: props.disabled ? 'default' : 'pointer' }}
				>
					<img id="imageRef" alt="Profile Image" src={giguser ? giguser.imageUrl : '/frog.png'} />
					<input ref={fileInputRef} className="FileInput" type="file" multiple onChange={onFilesAdded} />
				</div>
			</CardContent>
		</Card>
	);
}

FileDropzone.propTypes = {};

export default FileDropzone;
