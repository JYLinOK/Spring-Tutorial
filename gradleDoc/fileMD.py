import os
import platform

## Author Jinwei Lin

print("======= Get this files structure =======")











platform_sys = platform.system()
print(f'{platform_sys = }')

this_dir = os.path.dirname(os.path.abspath(__file__))
this_dir_list = os.listdir(this_dir)

if platform_sys == 'Windows':
    this_dir_name = this_dir.split('\\')[-1]
else:
    this_dir_name = this_dir.split('//')[-1]

if platform_sys == 'Windows':
    Backslash = '\\'
else:
    Backslash = '/'

GitHub_Backslash = True
if GitHub_Backslash:
    Backslash = '/'

print(f'{this_dir = }')
print(f'{this_dir_name = }')
print(f'{this_dir_list = }')
md_files_L = [] 

##### [Title](mainDir/file_name.md)

for file in this_dir_list:
    if file.endswith(".md"):
        first_dot_idx = file[::-1].index('.')+1
        file_str = '##### [' + str(file[:-first_dot_idx]) + '](' + this_dir_name + Backslash + file + ')'
        md_files_L.append(file_str)

print(f'{md_files_L = }')

with open(os.path.join(this_dir, 'md-list.txt'), 'w', encoding='utf-8') as F:
    # Write Title
    F.write('### ' + this_dir_name + '\n') 

    for md_F in md_files_L:
        F.write(md_F + '\n')
    F.close()
















