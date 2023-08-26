```
PC-Sync - An (ideally) boot-time program to reverse a filesystem back to a specified state

=== Usage

To get this working:
- Boot into the computer you want to use as server
- Plug in a drive and share it to the local network
- In that drive, put a 'config.pc-sync.txt' using the config format specified below
- In that drive, put a 'dump.reg' representing a registry dump
- In that drive, put as many folders as the drives selected in the config file, each one names as if they were a drive (ex: C:\)
- IMPORTANT! Make sure these files are read-only!
- Boot into the computer you want to use as target
- Mount the drive shared on the network with a label of Z:\
- Set the program to start at boot

=== Config Format

[File: config.pc-sync.txt]

SELECTED_DRIVES = C:\
SELECTED_DRIVES = D:\
SELECTED_DRIVES = E:\
```