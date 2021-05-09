export const mapProfileToContact = (profile, giguser) => {
	const contact = {
		userId: giguser.id,
		contactId: profile.userId,
		contactName: profile.email,
		contactImageUrl: profile.userImageUrl,
	};
	return contact;
};
