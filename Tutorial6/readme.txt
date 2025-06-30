format of nodes.txt:
id position.x position.y

format of edges.txt:
start_id goal_id edgeType

comments:
- position.x, position.y are given in [m] w.r.t. the lower left corner of the map.
- edgeType is one of the following strings (refer to http://wiki.openstreetmap.org/wiki/DE:Key:highway)
	primary
	primaryLink
	secondary
	secondaryLink
	tertiary
	unclassified
	residential
	livingStreet
	path
	cycleway
	rack
	footway
	track
	notKnownYet